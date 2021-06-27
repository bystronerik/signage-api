/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.service;

import com.deizon.services.model.Validity;
import com.deizon.frydasignagesoftware.model.alert.Alert;
import com.deizon.frydasignagesoftware.model.directory.CreateDirectoryInput;
import com.deizon.frydasignagesoftware.model.directory.Directory;
import com.deizon.frydasignagesoftware.model.directory.FindDirectoryInput;
import com.deizon.frydasignagesoftware.model.directory.UpdateDirectoryInput;
import com.deizon.frydasignagesoftware.repository.DirectoryRepository;
import com.deizon.services.service.BaseService;
import com.deizon.services.util.EntityBuilder;
import com.deizon.services.util.ExampleBuilder;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class DirectoryService
        extends BaseService<
                Directory,
                CreateDirectoryInput,
                UpdateDirectoryInput,
                FindDirectoryInput,
                DirectoryRepository> {

    public DirectoryService(DirectoryRepository repository) {
        super(Directory.class, repository, CreateDirectoryInput.class, UpdateDirectoryInput.class);
    }

    @Override
    protected Example<Directory> createExample(FindDirectoryInput input) {
        final Directory data = new Directory();
        return new ExampleBuilder<>(data)
                .exact()
                .stringField("id", input::getId, data::setId)
                .stringField("name", input::getName, data::setName)
                .stringField("parentDirectory", input::getParentDirectory, data::setParentDirectory)
                .booleanField("deleted", () -> false, data::setDeleted)
                .create();
    }

    @Override
    protected Directory processData(Directory entity, UpdateDirectoryInput data) {
        return super.processData(new EntityBuilder<>(entity)
                .stringField(data::getName, entity::setName)
                .stringField(data::getParentDirectory, entity::setParentDirectory)
                .getEntity(), data);
    }

}
