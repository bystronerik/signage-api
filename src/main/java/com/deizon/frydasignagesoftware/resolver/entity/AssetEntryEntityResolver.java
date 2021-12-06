package com.deizon.frydasignagesoftware.resolver.entity;

import com.deizon.frydasignagesoftware.model.AssetEntry;
import com.deizon.frydasignagesoftware.model.asset.Asset;
import com.deizon.frydasignagesoftware.model.style.Style;
import com.deizon.frydasignagesoftware.repository.AssetRepository;
import com.deizon.frydasignagesoftware.repository.StyleRepository;
import com.deizon.services.exception.ItemNotFoundException;
import com.deizon.services.resolver.BaseEntityResolver;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AssetEntryEntityResolver extends BaseEntityResolver<AssetEntry> {

    private final AssetRepository assetRepository;
    private final StyleRepository styleRepository;

    public CompletableFuture<Asset> getAsset(AssetEntry assetEntry) {
        return this.processAsync(
                () ->
                        assetRepository
                                .findById(assetEntry.getAsset())
                                .orElseThrow(() -> new ItemNotFoundException(Asset.class)));
    }

    public CompletableFuture<Style> getAnimationIn(AssetEntry assetEntry) {
        return this.processAsync(
                () -> {
                    if (assetEntry.getAnimationIn() == null) return null;

                    return styleRepository
                            .findById(assetEntry.getAnimationIn())
                            .orElseThrow(() -> new ItemNotFoundException(Style.class));
                });
    }

    public CompletableFuture<Style> getAnimationOut(AssetEntry assetEntry) {
        return this.processAsync(
                () -> {
                    if (assetEntry.getAnimationOut() == null) return null;

                    return styleRepository
                            .findById(assetEntry.getAnimationOut())
                            .orElseThrow(() -> new ItemNotFoundException(Style.class));
                });
    }
}
