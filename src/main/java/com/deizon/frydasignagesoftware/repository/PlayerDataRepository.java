/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.repository;

import com.deizon.frydasignagesoftware.model.deploydata.PlayerData;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface PlayerDataRepository extends MongoRepository<PlayerData, String> {

    @Query("{'group': ?0, 'versionHash': ?1}")
    Optional<PlayerData> findByVersion(String group, String versionHash);
}
