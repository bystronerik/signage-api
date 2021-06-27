/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.service;

import com.deizon.frydasignagesoftware.exception.UnsupportedFileType;
import com.deizon.services.model.FileUpload;
import com.deizon.services.model.Validity;
import com.deizon.frydasignagesoftware.model.asset.Asset;
import com.deizon.frydasignagesoftware.model.asset.CreateAssetInput;
import com.deizon.frydasignagesoftware.model.asset.FindAssetInput;
import com.deizon.frydasignagesoftware.model.asset.UpdateAssetInput;
import com.deizon.frydasignagesoftware.repository.AssetListRepository;
import com.deizon.frydasignagesoftware.repository.AssetRepository;
import com.deizon.services.exception.ItemNotFoundException;
import com.deizon.services.service.BaseService;
import com.deizon.services.util.EntityBuilder;
import com.deizon.services.util.ExampleBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
public class AssetService
        extends BaseService<
                Asset, CreateAssetInput, UpdateAssetInput, FindAssetInput, AssetRepository> {

    private final ResourceLoader resourceLoader;
    private final Environment environment;

    public AssetService(AssetRepository assetRepository,
                        ResourceLoader resourceLoader, Environment environment) {
        super(Asset.class, assetRepository, CreateAssetInput.class, UpdateAssetInput.class);

        this.resourceLoader = resourceLoader;
        this.environment = environment;
    }

    public Asset create(CreateAssetInput input, FileUpload file) throws IOException {
        final Asset entity = new Asset();

        if (file != null) {
            this.processAssetFile(entity, file);
        }

        return this.processData(entity, input);
    }

    public Asset update(String id, UpdateAssetInput input, FileUpload file) throws IOException {
        final Asset entity = this.repository.findById(id).orElseThrow(() -> new ItemNotFoundException(this.entityClass));

        if (file != null) {
            this.processAssetFile(entity, file);
        }

        return this.processData(entity, input);
    }

    @Override
    protected Example<Asset> createExample(FindAssetInput input) {
        final Asset data = new Asset();
        //noinspection unchecked
        return new ExampleBuilder<>(data)
                .exact()
                .stringField("id", input::getId, data::setId)
                .stringField("name", input::getName, data::setName)
                .enumField("type", input::getType, (val) -> data.setType((Asset.Type) val))
                .stringField("directory", input::getDirectory, data::setDirectory)
                .stringField("path", input::getPath, data::setPath)
                .booleanField("deleted", () -> false, data::setDeleted)
                .listField("tags", input::getTags, (val) -> data.setTags((List<String>) val))
                .create();
    }

    @Override
    protected Asset processData(Asset entity, UpdateAssetInput data) {
        final Asset finalEntity = entity;
        entity = new EntityBuilder<>(entity)
                .stringField(data::getName, entity::setName)
                .integerField(data::getShowTime, entity::setShowTime)
                .objectField(data::getValidity, (val) -> finalEntity.setValidity((Validity) val))
                .stringField(data::getAnimationIn, entity::setAnimationIn)
                .stringField(data::getAnimationOut, entity::setAnimationOut)
                .getEntity();
        return super.processData(entity, data);
    }

    private void processAssetFile(Asset entity, FileUpload file) throws IOException {
        final String type = this.getType(file.getContentType());
        final String fileName = RandomStringUtils.random(20, true, true) + "." + type;

        // TODO předělat pak na MediaType
        switch (file.getContentType()) {
            case "image/png":
                entity.setType(Asset.Type.IMAGE_PNG);
                break;
            case "image/jpeg":
                entity.setType(Asset.Type.IMAGE_JPG);
                break;
            case "image/gif":
                entity.setType(Asset.Type.IMAGE_GIF);
                break;
            case "video/mp4":
                entity.setType(Asset.Type.VIDEO_MP4);
                break;
            case "video/quicktime":
                entity.setType(Asset.Type.VIDEO_MOV);
                break;
            case "video/x-msvideo":
                entity.setType(Asset.Type.VIDEO_AVI);
                break;
            case "video/x-ms-wmv":
                entity.setType(Asset.Type.VIDEO_WMV);
                break;
            default:
                throw new UnsupportedFileType();
        }

        try (FileOutputStream out = new FileOutputStream(getLocation(fileName))) {
            IOUtils.copy(file.getContent(), out);
        }

        entity.setPath(getPublicURL(fileName));
    }

    private File getLocation(String filename) throws IOException {
        return new File(resourceLoader.getResource("file:./data/").getFile(), filename);
    }

    public String getPublicURL(String fileLocation) {
        return MessageFormat.format(
                "{0}/storage/{1}", environment.getRequiredProperty("api.location"), fileLocation);
    }

    private String getType(String mimetype) {
        return MediaType.parseMediaType(mimetype).getSubtype();
    }
}
