/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.resolver;

import com.deizon.frydasignagesoftware.exception.ItemNotFoundException;
import com.deizon.frydasignagesoftware.exception.UnsupportedFileType;
import com.deizon.services.model.FileUpload;

import com.deizon.frydasignagesoftware.model.asset.Asset;
import com.deizon.frydasignagesoftware.model.asset.CreateAssetInput;
import com.deizon.frydasignagesoftware.model.asset.FindAssetInput;
import com.deizon.frydasignagesoftware.model.asset.UpdateAssetInput;
import com.deizon.frydasignagesoftware.repository.AssetListRepository;
import com.deizon.frydasignagesoftware.repository.AssetRepository;
import com.deizon.frydasignagesoftware.service.AssetService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@PreAuthorize("isAuthenticated()")
public class AssetResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final AssetService service;

    public Iterable<Asset> findAllAssets(FindAssetInput input) {
        return this.service.findAll(input);
    }

    public Asset findAsset(FindAssetInput input) {
        return this.service.find(input);
    }

    public Asset createAsset(CreateAssetInput data, FileUpload file) throws IOException {
        return this.service.create(data, file);
    }

    public Asset updateAsset(String id, UpdateAssetInput data, FileUpload file) throws IOException {
        return this.service.update(id, data, file);
    }

    public Asset deleteAsset(String id) {
        return this.service.delete(id);
    }

    public boolean totalDeleteAsset(String id) {
        return this.service.totalDelete(id);
    }

}
