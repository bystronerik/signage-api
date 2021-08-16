/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.deizon.frydasignagesoftware.model.auth.Token;
import com.deizon.frydasignagesoftware.model.user.User;
import com.deizon.frydasignagesoftware.repository.UserRepository;
import com.deizon.services.exception.BadTokenException;
import java.time.Instant;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthUserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final SecurityProperties properties;
    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    @Override
    @Transactional
    public JWTUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(user -> getUserDetails(user, getToken(user)))
                .orElseThrow(
                        () -> new UsernameNotFoundException("Username or password didn't match"));
    }

    @Transactional
    public JWTUserDetails loadUserByToken(String token) {
        return getDecodedToken(token)
                .map(DecodedJWT::getSubject)
                .flatMap(userRepository::findByUsername)
                .map(user -> getUserDetails(user, token))
                .orElseThrow(BadTokenException::new);
    }

    @Transactional
    public String getToken(User user) {
        Instant now = Instant.now();
        Instant expiry = Instant.now().plus(properties.getTokenExpiration());
        return JWT.create()
                .withIssuer(properties.getTokenIssuer())
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(expiry))
                .withSubject(user.getUsername())
                .withClaim("role", user.getRole().getName())
                .sign(algorithm);
    }

    @Transactional
    public Token getTokenObject(User user, Boolean shortSession) {
        Instant expiry =
                Instant.now()
                        .plus(
                                shortSession
                                        ? properties.getShortTokenExpiration()
                                        : properties.getTokenExpiration());
        return Token.builder().accessToken(getToken(user)).expiresIn(expiry).build();
    }

    @Transactional
    public User getCurrentUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getName)
                .flatMap(userRepository::findByUsername)
                .orElse(null);
    }

    private JWTUserDetails getUserDetails(User user, String token) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName());

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);

        return JWTUserDetails.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .token(token)
                .build();
    }

    private Optional<DecodedJWT> getDecodedToken(String token) {
        try {
            return Optional.of(verifier.verify(token));
        } catch (JWTVerificationException ex) {
            return Optional.empty();
        }
    }
}
