/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.repository;

import com.deizon.frydasignagesoftware.model.playerinstance.PlayerInstance;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerInstanceRepository extends MongoRepository<PlayerInstance, String> {}
