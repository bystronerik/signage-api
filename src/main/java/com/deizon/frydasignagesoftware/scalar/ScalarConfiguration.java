package com.deizon.frydasignagesoftware.scalar;

import com.deizon.services.scalar.DataTypeScalarCoercing;
import com.deizon.services.scalar.DateTimeScalarCoercing;
import com.deizon.services.scalar.FileTypeScalarCoercing;
import com.deizon.services.scalar.FileUploadScalarCoercing;
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
    public GraphQLScalarType dateTimeScalar() {
        return GraphQLScalarType.newScalar()
                .name("Date")
                .description("Java Instant as scalar. DateTime value field.")
                .coercing(new DateTimeScalarCoercing())
                .build();
    }

    @Bean
    public GraphQLScalarType fileTypeScalar() {
        return GraphQLScalarType.newScalar()
                .name("FileType")
                .description("FileType enum as scalar. File type value field.")
                .coercing(new FileTypeScalarCoercing())
                .build();
    }

    @Bean
    public GraphQLScalarType dataTypeScalar() {
        return GraphQLScalarType.newScalar()
                .name("DataType")
                .description("DataType enum as scalar. Data type value field.")
                .coercing(new DataTypeScalarCoercing())
                .build();
    }
}
