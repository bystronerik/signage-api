/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.service;

import com.deizon.frydasignagesoftware.exception.UserAlreadyExistsException;
import com.deizon.frydasignagesoftware.model.user.CreateUserInput;
import com.deizon.frydasignagesoftware.model.user.FindUserInput;
import com.deizon.frydasignagesoftware.model.user.UpdateUserInput;
import com.deizon.frydasignagesoftware.model.user.User;
import com.deizon.frydasignagesoftware.repository.UserRepository;
import com.deizon.services.service.BaseService;
import com.deizon.services.util.EntityBuilder;
import com.deizon.services.util.ExampleBuilder;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService
        extends BaseService<User, CreateUserInput, UpdateUserInput, FindUserInput, UserRepository> {

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        super(User.class, repository, CreateUserInput.class, UpdateUserInput.class);

        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected Example<User> createExample(FindUserInput input) {
        final User data = new User();
        return new ExampleBuilder<>(data)
                .exact()
                .field("id", input::getId, data::setId)
                .field("username", input::getUsername, data::setUsername)
                .field("password", input::getPassword, data::setPassword)
                .field("role", input::getRole, data::setRole)
                .field("deleted", () -> false, data::setDeleted)
                .create();
    }

    @Override
    protected User processData(User entity, UpdateUserInput data) {
        return super.processData(
                new EntityBuilder<>(entity)
                        .field(
                                () -> {
                                    if (data instanceof CreateUserInput) {
                                        if (this.repository
                                                .findByUsername(data.getUsername())
                                                .isPresent())
                                            throw new UserAlreadyExistsException();
                                    } else {
                                        if (data.getUsername() != null) {
                                            if (!entity.getUsername().equals(data.getUsername())) {
                                                if (this.repository
                                                        .findByUsername(data.getUsername())
                                                        .isPresent()) {
                                                    throw new UserAlreadyExistsException();
                                                }
                                            }
                                        }
                                    }

                                    return data.getUsername();
                                },
                                entity::setUsername)
                        .field(
                                () -> passwordEncoder.encode(data.getPassword()),
                                entity::setPassword)
                        .field(
                                () -> {
                                    if (data instanceof CreateUserInput && data.getRole() == null)
                                        return User.Role.USER;
                                    return data.getRole();
                                },
                                entity::setRole)
                        .getEntity(),
                data);
    }
}
