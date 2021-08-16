/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.resolver.entity;

import com.deizon.frydasignagesoftware.model.user.User;
import graphql.kickstart.tools.GraphQLResolver;
import java.util.concurrent.CompletableFuture;

public class UserEntityResolver implements GraphQLResolver<User> {

    public CompletableFuture<String> getRole(User user) {
        return CompletableFuture.completedFuture(user.getRole().getName());
    }
}
