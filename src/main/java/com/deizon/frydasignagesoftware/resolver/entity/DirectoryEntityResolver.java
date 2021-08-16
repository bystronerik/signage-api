/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.resolver.entity;

import com.deizon.frydasignagesoftware.model.directory.Directory;
import com.deizon.frydasignagesoftware.repository.DirectoryRepository;
import com.deizon.services.exception.ItemNotFoundException;
import com.deizon.services.resolver.BaseResolver;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DirectoryEntityResolver extends BaseResolver<Directory> {

    private final DirectoryRepository directoryRepository;

    public CompletableFuture<Directory> getParentDirectory(Directory directory) {
        return this.processAsync(
                () -> {
                    if (directory.getParentDirectory() == null
                            || directory.getParentDirectory().equals("root")) {
                        return null;
                    }

                    return directoryRepository
                            .findById(directory.getParentDirectory())
                            .orElseThrow(() -> new ItemNotFoundException(Directory.class));
                });
    }
}
