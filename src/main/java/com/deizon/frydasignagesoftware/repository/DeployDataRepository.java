package com.deizon.frydasignagesoftware.repository;

import com.deizon.frydasignagesoftware.model.deploydata.DeployData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeployDataRepository extends MongoRepository<DeployData, String> {}
