/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.resolver.entity;

import com.deizon.frydasignagesoftware.exception.ItemNotFoundException;
import com.deizon.frydasignagesoftware.model.directory.Directory;
import com.deizon.frydasignagesoftware.repository.DirectoryRepository;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DirectoryEntityResolver implements GraphQLResolver<Directory> {

    private final DirectoryRepository directoryRepository;

    public Directory getParentDirectory(Directory directory) {
        if (directory.getParentDirectory() == null) {
            return null;
        }

        return directoryRepository
                .findById(directory.getParentDirectory())
                .orElseThrow(() -> new ItemNotFoundException(Directory.class));
    }
}
