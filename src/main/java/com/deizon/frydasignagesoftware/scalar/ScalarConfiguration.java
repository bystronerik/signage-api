/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.scalar;

import graphql.schema.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScalarConfiguration {

    @Bean
    public GraphQLScalarType uploadScalar() {
        return GraphQLScalarType.newScalar()
                .name("FileUpload")
                .description("Custom FileUpload class as scalar.")
                .coercing(new FileUploadScalarCoercing())
                .build();
    }

    @Bean
    public GraphQLScalarType dateScalar() {
        return GraphQLScalarType.newScalar()
                .name("Date")
                .description("Java Date as scalar.")
                .coercing(new DateScalarCoercing())
                .build();
    }
}
