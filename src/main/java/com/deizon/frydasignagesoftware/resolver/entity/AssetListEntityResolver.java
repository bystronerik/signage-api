/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.resolver.entity;

import com.deizon.frydasignagesoftware.model.assetlist.AssetList;
import com.deizon.frydasignagesoftware.model.style.Style;
import com.deizon.frydasignagesoftware.repository.StyleRepository;
import com.deizon.services.exception.ItemNotFoundException;
import com.deizon.services.resolver.BaseResolver;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AssetListEntityResolver extends BaseResolver<AssetList> {

    private final StyleRepository styleRepository;

    public CompletableFuture<Style> getAnimationIn(AssetList assetList) {
        return this.processAsync(
                () ->
                        styleRepository
                                .findById(assetList.getAnimationIn())
                                .orElseThrow(() -> new ItemNotFoundException(Style.class)));
    }

    public CompletableFuture<Style> getAnimationOut(AssetList assetList) {
        return this.processAsync(
                () ->
                        styleRepository
                                .findById(assetList.getAnimationOut())
                                .orElseThrow(() -> new ItemNotFoundException(Style.class)));
    }
}
