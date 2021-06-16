/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.repository;

import com.deizon.frydasignagesoftware.model.player.Player;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface PlayerRepository extends MongoRepository<Player, String> {
    @NotNull
    @Override
    @Query("{'id': {$in: ?0}, 'deleted': false}")
    Iterable<Player> findAllById(@NotNull Iterable<String> ids);

    @NotNull
    @Override
    @Query("{'id': ?0, 'deleted': false}")
    Optional<Player> findById(@NotNull String id);

    @Query("{'token': ?0, 'deleted': false}")
    Optional<Player> findByToken(String token);
}
