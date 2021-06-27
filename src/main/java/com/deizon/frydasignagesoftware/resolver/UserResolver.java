/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.resolver;

import com.deizon.frydasignagesoftware.exception.ItemNotFoundException;
import com.deizon.frydasignagesoftware.exception.UserAlreadyExistsException;
import com.deizon.frydasignagesoftware.model.user.CreateUserInput;
import com.deizon.frydasignagesoftware.model.user.FindUserInput;
import com.deizon.frydasignagesoftware.model.user.UpdateUserInput;
import com.deizon.frydasignagesoftware.model.user.User;
import com.deizon.frydasignagesoftware.repository.UserRepository;
import com.deizon.frydasignagesoftware.service.UserService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@PreAuthorize("isAuthenticated()")
public class UserResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final UserService service;

    @PreAuthorize("hasAuthority('ADMIN')")
    public Iterable<User> findAllUsers(FindUserInput input) {
        return this.service.findAll(input);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public User findUser(FindUserInput input) {
        return this.service.find(input);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public User createUser(CreateUserInput data) {
        return this.service.create(data);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public User updateUser(String id, UpdateUserInput data) {
        return this.service.update(id, data);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public User deleteUser(String id) {
        return this.service.delete(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean totalDeleteUser(String id) {
        return this.service.totalDelete(id);
    }
}
