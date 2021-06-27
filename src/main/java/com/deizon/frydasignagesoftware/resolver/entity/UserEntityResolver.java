/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.resolver.entity;

import com.deizon.frydasignagesoftware.model.user.User;
import graphql.kickstart.tools.GraphQLResolver;

public class UserEntityResolver implements GraphQLResolver<User> {

    public String getRole(User user) {
        return user.getRole().getName();
    }
}
