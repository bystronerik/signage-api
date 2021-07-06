/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.resolver;

import com.deizon.frydasignagesoftware.model.directory.CreateDirectoryInput;
import com.deizon.frydasignagesoftware.model.directory.Directory;
import com.deizon.frydasignagesoftware.model.directory.FindDirectoryInput;
import com.deizon.frydasignagesoftware.model.directory.UpdateDirectoryInput;
import com.deizon.frydasignagesoftware.repository.*;
import com.deizon.frydasignagesoftware.service.DirectoryService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@PreAuthorize("isAuthenticated()")
public class DirectoryResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final DirectoryService service;

    public Iterable<Directory> findAllDirectories(FindDirectoryInput input) {
        return this.service.findAll(input);
    }

    public Directory findDirectory(FindDirectoryInput input) {
        return this.service.find(input);
    }

    public Directory createDirectory(CreateDirectoryInput data) {
        return this.service.create(data);
    }

    public Directory updateDirectory(String id, UpdateDirectoryInput data) {
        return this.service.update(id, data);
    }

    public Directory deleteDirectory(String id) {
        return this.service.delete(id);
    }

    public boolean totalDeleteDirectory(String id) {
        return this.service.totalDelete(id);
    }
}
