package com.deizon.frydasignagesoftware.service;

import com.deizon.frydasignagesoftware.model.asset.Asset;
import com.deizon.frydasignagesoftware.model.asset.CreateAssetInput;
import com.deizon.frydasignagesoftware.model.asset.FindAssetInput;
import com.deizon.frydasignagesoftware.model.asset.UpdateAssetInput;
import com.deizon.frydasignagesoftware.model.directory.Directory;
import com.deizon.frydasignagesoftware.repository.AssetListRepository;
import com.deizon.frydasignagesoftware.repository.AssetRepository;
import com.deizon.frydasignagesoftware.repository.DirectoryRepository;
import com.deizon.services.exception.ItemNotFoundException;
import com.deizon.services.exception.UnsupportedFileType;
import com.deizon.services.minio.service.MinioService;
import com.deizon.services.model.FileUpload;
import com.deizon.services.model.file.FileType;
import com.deizon.services.service.BaseService;
import com.deizon.services.util.EntityBuilder;
import com.deizon.services.util.ExampleBuilder;
import java.io.IOException;
import java.time.Instant;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class AssetService
        extends BaseService<
                Asset, CreateAssetInput, UpdateAssetInput, FindAssetInput, AssetRepository> {

    private final AssetListRepository assetListRepository;
    private final DirectoryRepository directoryRepository;
    private final MinioService minioService;

    public AssetService(
            AssetRepository assetRepository,
            DirectoryRepository directoryRepository,
            AssetListRepository assetListRepository,
            MinioService minioService) {
        super(Asset.class, assetRepository, CreateAssetInput.class, UpdateAssetInput.class);

        this.assetListRepository = assetListRepository;
        this.directoryRepository = directoryRepository;
        this.minioService = minioService;
    }

    public Asset create(CreateAssetInput input, FileUpload file) throws IOException {
        final Asset entity = new Asset();

        if (file != null) {
            this.processAssetFile(entity, file);
        }

        if (input.getDirectory() == null) {
            input.setDirectory(
                    this.directoryRepository
                            .findRoot()
                            .orElseThrow(() -> new ItemNotFoundException(Directory.class))
                            .getId());
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
        final String fileName =
                RandomStringUtils.random(20, true, true) + "." + file.getMediaType().getSubtype();

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

        entity.setPath(minioService.putObject(fileName, file, null));
    }
}
