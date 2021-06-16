/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.repository;

import com.deizon.frydasignagesoftware.model.asset.Asset;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface AssetRepository extends MongoRepository<Asset, String> {
    @NotNull
    @Override
    @Query("{'id': {$in: ?0}, 'deleted': false}")
    Iterable<Asset> findAllById(@NotNull Iterable<String> ids);

    @NotNull
    @Override
    @Query("{'id': ?0, 'deleted': false}")
    Optional<Asset> findById(@NotNull String id);
}
