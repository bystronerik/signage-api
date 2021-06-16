/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.repository;

import com.deizon.frydasignagesoftware.model.style.Style;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface StyleRepository extends MongoRepository<Style, String> {
    @NotNull
    @Override
    @Query("{'id': {$in: ?0}, 'deleted': false}")
    Iterable<Style> findAllById(@NotNull Iterable<String> ids);

    @NotNull
    @Override
    @Query("{'id': ?0, 'deleted': false}")
    Optional<Style> findById(@NotNull String id);
}
