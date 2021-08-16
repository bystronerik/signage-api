/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.service;

import com.deizon.frydasignagesoftware.model.directory.CreateDirectoryInput;
import com.deizon.frydasignagesoftware.model.directory.Directory;
import com.deizon.frydasignagesoftware.model.directory.FindDirectoryInput;
import com.deizon.frydasignagesoftware.model.directory.UpdateDirectoryInput;
import com.deizon.frydasignagesoftware.repository.AssetRepository;
import com.deizon.frydasignagesoftware.repository.DirectoryRepository;
import com.deizon.services.exception.ItemNotFoundException;
import com.deizon.services.service.BaseService;
import com.deizon.services.util.EntityBuilder;
import com.deizon.services.util.ExampleBuilder;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class DirectoryService
        extends BaseService<
                Directory,
                CreateDirectoryInput,
                UpdateDirectoryInput,
                FindDirectoryInput,
                DirectoryRepository> {

    private final AssetRepository assetRepository;

    public DirectoryService(DirectoryRepository repository, AssetRepository assetRepository) {
        super(Directory.class, repository, CreateDirectoryInput.class, UpdateDirectoryInput.class);
        this.assetRepository = assetRepository;
    }

    @Override
    protected Example<Directory> createExample(FindDirectoryInput input) {
        final Directory data = new Directory();
        return new ExampleBuilder<>(data)
                .exact()
                .field("id", input::getId, data::setId)
                .field("name", input::getName, data::setName)
                .field("parentDirectory", input::getParentDirectory, data::setParentDirectory)
                .field("deleted", () -> false, data::setDeleted)
                .create();
    }

    @Override
    protected Directory processData(Directory entity, UpdateDirectoryInput data) {
        return super.processData(
                new EntityBuilder<>(entity)
                        .field(data::getName, entity::setName)
                        .field(data::getParentDirectory, entity::setParentDirectory)
                        .getEntity(),
                data);
    }

    @Override
    public Directory delete(String id) {
        final Directory data = super.delete(id);
        final Directory root =
                this.repository
                        .findRoot()
                        .orElseThrow(
                                () ->
                                        new ItemNotFoundException(
                                                "Invalid directories state, can not find root directory."));

        this.assetRepository
                .findAllByDirectory(data.getId())
                .forEach(
                        item -> {
                            item.setDirectory(root.getId());
                            this.assetRepository.save(item);
                        });

        return data;
    }
}
