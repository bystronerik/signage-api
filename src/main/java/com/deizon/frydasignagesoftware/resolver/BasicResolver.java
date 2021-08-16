/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.resolver;

import com.deizon.frydasignagesoftware.model.auth.LoginDetails;
import com.deizon.frydasignagesoftware.model.user.User;
import com.deizon.frydasignagesoftware.repository.UserRepository;
import com.deizon.frydasignagesoftware.security.AuthUserService;
import com.deizon.services.exception.BadCredentialsException;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@PreAuthorize("isAuthenticated()")
public class BasicResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthUserService authUserService;

    @PreAuthorize("isAnonymous()")
    public LoginDetails login(String username, String password, Boolean shortSession) {
        User user =
                userRepository.findByUsername(username).orElseThrow(BadCredentialsException::new);

        if (passwordEncoder.matches(password, user.getPassword())) {
            return new LoginDetails(user, authUserService.getTokenObject(user, shortSession));
        } else {
            throw new BadCredentialsException();
        }
    }
}
