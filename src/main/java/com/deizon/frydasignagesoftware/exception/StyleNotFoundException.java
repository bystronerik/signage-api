package com.deizon.frydasignagesoftware.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import java.util.List;

public class StyleNotFoundException extends RuntimeException implements GraphQLError, APIException {

    public StyleNotFoundException() {
        super("We were unable to find a animation with the provided information");
    }

    public StyleNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorType getErrorType() {
        return null;
    }
}
