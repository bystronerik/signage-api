package com.deizon.frydasignagesoftware.security;

import java.time.Duration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "api.security")
@Getter
@RequiredArgsConstructor
public class SecurityProperties {

    /** Amount of hashing iterations, where formula is 2^passwordStrength iterations */
    private final int passwordStrength;

    /** Secret used to generate and verify JWT tokens */
    private final String tokenSecret;

    /** Name of the token issuer */
    private final String tokenIssuer = "whoiswho";

    /** Duration after which a token will expire */
    private final Duration shortTokenExpiration = Duration.ofHours(2);

    /** Duration after which a token will expire */
    private final Duration tokenExpiration = Duration.ofDays(30);
}
