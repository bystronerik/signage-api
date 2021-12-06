package com.deizon.frydasignagesoftware.resolver.entity;

import com.deizon.frydasignagesoftware.model.alert.Alert;
import com.deizon.frydasignagesoftware.model.asset.Asset;
import com.deizon.frydasignagesoftware.model.assetlist.AssetList;
import com.deizon.frydasignagesoftware.model.directory.Directory;
import com.deizon.frydasignagesoftware.model.style.Style;
import com.deizon.frydasignagesoftware.model.tag.Tag;
import com.deizon.frydasignagesoftware.repository.*;
import com.deizon.services.exception.ItemNotFoundException;
import com.deizon.services.resolver.BaseEntityResolver;
import java.text.MessageFormat;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AssetEntityResolver extends BaseEntityResolver<Asset> {

    private final AssetListRepository assetListRepository;
    private final StyleRepository styleRepository;
    private final AlertRepository alertRepository;
    private final DirectoryRepository directoryRepository;
    private final TagRepository tagRepository;

    private final Environment environment;

    public CompletableFuture<Iterable<AssetList>> getAssetLists(Asset asset) {
        return this.processAsync(() -> assetListRepository.findAllByAsset(asset.getId()));
    }

    public CompletableFuture<Alert> getAlert(Asset asset) {
        return this.processAsync(
                () -> {
                    if (asset.getAlert() == null) return null;

                    return this.alertRepository
                            .findById(asset.getAlert())
                            .orElseThrow(() -> new ItemNotFoundException(Alert.class));
                });
    }

    public CompletableFuture<Style> getAnimationIn(Asset asset) {
        return this.processAsync(
                () -> {
                    if (asset.getAnimationIn() == null) return null;

                    return this.styleRepository
                            .findById(asset.getAnimationIn())
                            .orElseThrow(() -> new ItemNotFoundException(Style.class));
                });
    }

    public CompletableFuture<Style> getAnimationOut(Asset asset) {
        return this.processAsync(
                () -> {
                    if (asset.getAnimationOut() == null) return null;

                    return this.styleRepository
                            .findById(asset.getAnimationOut())
                            .orElseThrow(() -> new ItemNotFoundException(Style.class));
                });
    }

    public CompletableFuture<Directory> getDirectory(Asset asset) {
        return this.processAsync(
                () -> {
                    if (asset.getDirectory() == null) return null;

                    return this.directoryRepository
                            .findById(asset.getDirectory())
                            .orElseThrow(() -> new ItemNotFoundException(Directory.class));
                });
    }

    public CompletableFuture<Iterable<Tag>> getTags(Asset asset) {
        return this.processAsync(() -> this.tagRepository.findAllById(asset.getTags()));
    }

    public CompletableFuture<String> getPath(Asset asset) {
        return this.processAsync(
                () ->
                        MessageFormat.format(
                                "{0}/{1}",
                                this.environment.getRequiredProperty("storage.location"),
                                asset.getPath()));
    }
}
