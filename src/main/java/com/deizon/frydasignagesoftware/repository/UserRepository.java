package com.deizon.frydasignagesoftware.repository;

import com.deizon.frydasignagesoftware.model.user.User;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String> {
    @NotNull
    @Override
    @Query("{'id': {$in: ?0}, 'deleted': false}")
    Iterable<User> findAllById(@NotNull Iterable<String> ids);

    @NotNull
    @Override
    @Query("{'id': ?0, 'deleted': false}")
    Optional<User> findById(@NotNull String id);

    @Query("{'username': ?0, 'deleted': false}")
    Optional<User> findByUsername(String username);
}
