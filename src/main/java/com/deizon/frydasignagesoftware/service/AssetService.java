/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.service;

import com.deizon.frydasignagesoftware.model.asset.Asset;
import com.deizon.frydasignagesoftware.model.asset.CreateAssetInput;
import com.deizon.frydasignagesoftware.model.asset.FindAssetInput;
import com.deizon.frydasignagesoftware.model.asset.UpdateAssetInput;
import com.deizon.frydasignagesoftware.repository.AssetListRepository;
import com.deizon.frydasignagesoftware.repository.AssetRepository;
import com.deizon.services.exception.ItemNotFoundException;
import com.deizon.services.exception.UnsupportedFileType;
import com.deizon.services.model.FileUpload;
import com.deizon.services.model.file.FileType;
import com.deizon.services.service.BaseService;
import com.deizon.services.util.EntityBuilder;
import com.deizon.services.util.ExampleBuilder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
public class AssetService
        extends BaseService<
                Asset, CreateAssetInput, UpdateAssetInput, FindAssetInput, AssetRepository> {

    private final AssetListRepository assetListRepository;
    private final ResourceLoader resourceLoader;

    public AssetService(
            AssetRepository assetRepository,
            AssetListRepository assetListRepository,
            ResourceLoader resourceLoader) {
        super(Asset.class, assetRepository, CreateAssetInput.class, UpdateAssetInput.class);

        this.assetListRepository = assetListRepository;
        this.resourceLoader = resourceLoader;
    }

    public Asset create(CreateAssetInput input, FileUpload file) throws IOException {
        final Asset entity = new Asset();

        if (file != null) {
            this.processAssetFile(entity, file);
        }

        return this.processData(entity, input);
    }

    public Asset update(String id, UpdateAssetInput input, FileUpload file) throws IOException {
        final Asset entity =
                this.repository
                        .findById(id)
                        .orElseThrow(() -> new ItemNotFoundException(this.entityClass));

        if (file != null) {
            this.processAssetFile(entity, file);
        }

        return this.processData(entity, input);
    }

    @Override
    public Asset delete(String id) {
        assetListRepository
                .findAllByAsset(id)
                .forEach(
                        assetList -> {
                            assetList
                                    .getAssets()
                                    .removeIf(assetEntry -> assetEntry.getAsset().equals(id));
                            assetList.setUpdateDate(Instant.now());
                            assetListRepository.save(assetList);
                        });

        return super.delete(id);
    }

    @Override
    protected Example<Asset> createExample(FindAssetInput input) {
        final Asset data = new Asset();
        return new ExampleBuilder<>(data)
                .exact()
                .field("id", input::getId, data::setId)
                .field("name", input::getName, data::setName)
                .field("type", input::getType, data::setType)
                .field("directory", input::getDirectory, data::setDirectory)
                .field("path", input::getPath, data::setPath)
                .field("deleted", () -> false, data::setDeleted)
                .listField("tags", input::getTags, data::setTags)
                .create();
    }

    @Override
    protected Asset processData(Asset entity, UpdateAssetInput data) {
        final Asset finalEntity = entity;
        entity =
                new EntityBuilder<>(entity)
                        .field(data::getName, entity::setName)
                        .field(data::getShowTime, entity::setShowTime)
                        .field(data::getValidity, finalEntity::setValidity)
                        .field(data::getAnimationIn, entity::setAnimationIn)
                        .field(data::getAnimationOut, entity::setAnimationOut)
                        .listField(data::getTags, entity::getTags)
                        .field(data::getDirectory, entity::setDirectory)
                        .field(data::getAlert, entity::setAlert)
                        .getEntity();
        return super.processData(entity, data);
    }

    private void processAssetFile(Asset entity, FileUpload file) throws IOException {
        final String type = this.getType(file.getContentType());
        final String fileName = RandomStringUtils.random(20, true, true) + "." + type;

        // TODO předělat pak na MediaType
        switch (file.getContentType()) {
            case "image/png":
                entity.setType(FileType.IMAGE_PNG);
                break;
            case "image/jpeg":
                entity.setType(FileType.IMAGE_JPG);
                break;
            case "image/gif":
                entity.setType(FileType.IMAGE_GIF);
                break;
            case "video/mp4":
                entity.setType(FileType.VIDEO_MP4);
                break;
            case "video/quicktime":
                entity.setType(FileType.VIDEO_MOV);
                break;
            case "video/x-msvideo":
                entity.setType(FileType.VIDEO_AVI);
                break;
            case "video/x-ms-wmv":
                entity.setType(FileType.VIDEO_WMV);
                break;
            default:
                throw new UnsupportedFileType();
        }

        try (FileOutputStream out = new FileOutputStream(getLocation(fileName))) {
            IOUtils.copy(file.getContent(), out);
        }

        entity.setPath(fileName);
    }

    private File getLocation(String filename) throws IOException {
        return new File(resourceLoader.getResource("file:./data/").getFile(), filename);
    }

    private String getType(String mimetype) {
        return MediaType.parseMediaType(mimetype).getSubtype();
    }
}
