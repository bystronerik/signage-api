package com.deizon.frydasignagesoftware.resolver;

import com.deizon.frydasignagesoftware.model.directory.CreateDirectoryInput;
import com.deizon.frydasignagesoftware.model.directory.Directory;
import com.deizon.frydasignagesoftware.model.directory.FindDirectoryInput;
import com.deizon.frydasignagesoftware.model.directory.UpdateDirectoryInput;
import com.deizon.frydasignagesoftware.service.DirectoryService;
import com.deizon.services.resolver.BaseResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Log
@RequiredArgsConstructor
@Component
@PreAuthorize("isAuthenticated()")
public class DirectoryResolver extends BaseResolver {

    private final DirectoryService service;

    public Iterable<Directory> findAllDirectories(
            FindDirectoryInput input, DataFetchingEnvironment env) {
        log.info(this.getClientId(env));
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
