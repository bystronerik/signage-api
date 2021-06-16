/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.resolver;

import com.deizon.frydasignagesoftware.model.user.User;
import graphql.kickstart.tools.GraphQLResolver;

public class UserResolver implements GraphQLResolver<User> {

    public String getRole(User user) {
        return user.getRole().getName();
    }
}
