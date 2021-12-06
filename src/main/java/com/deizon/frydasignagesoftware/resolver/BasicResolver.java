package com.deizon.frydasignagesoftware.resolver;

import com.deizon.frydasignagesoftware.model.auth.LoginDetails;
import com.deizon.frydasignagesoftware.model.auth.Token;
import com.deizon.frydasignagesoftware.model.user.User;
import com.deizon.frydasignagesoftware.repository.UserRepository;
import com.deizon.services.auth.AuthService;
import com.deizon.services.exception.BadCredentialsException;
import com.deizon.services.jwt.JWTProperties;
import com.deizon.services.jwt.JWTUserDetails;
import com.deizon.services.resolver.BaseResolver;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@PreAuthorize("isAuthenticated()")
public class BasicResolver extends BaseResolver {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTProperties properties;
    private final AuthService authService;

    @PreAuthorize("isAnonymous()")
    public LoginDetails login(String username, String password, Boolean shortSession) {
        final User user =
                userRepository.findByUsername(username).orElseThrow(BadCredentialsException::new);

        if (passwordEncoder.matches(password, user.getPassword())) {
            return new LoginDetails(user, this.getTokenObject(user, shortSession));
        } else {
            throw new BadCredentialsException();
        }
    }

    private Token getTokenObject(User user, Boolean shortSession) {
        final Instant expiry =
                Instant.now()
                        .plus(
                                shortSession
                                        ? properties.getShortTokenExpiration()
                                        : properties.getTokenExpiration());
        return Token.builder()
                .accessToken(
                        this.authService.generateToken(
                                JWTUserDetails.builder().username(user.getUsername()).build(),
                                expiry))
                .expiresIn(expiry)
                .build();
    }
}
