/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.repository;

import com.deizon.frydasignagesoftware.model.assetlist.AssetList;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface AssetListRepository extends MongoRepository<AssetList, String> {
    @NotNull
    @Override
    @Query("{'id': {$in: ?0}, 'deleted': false}")
    Iterable<AssetList> findAllById(@NotNull Iterable<String> ids);

    @NotNull
    @Override
    @Query("{'id': ?0, 'deleted': false}")
    Optional<AssetList> findById(@NotNull String id);

    @Query("{'id': ?0, 'enabled': ?1, 'deleted': false}")
    Iterable<AssetList> findAllById(Iterable<String> id, Boolean enabled);

    @Query("{'assets.asset': ?0, 'deleted': false}")
    Iterable<AssetList> findAllByAsset(String assetId);
}
