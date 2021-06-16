/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.resolver;

import com.deizon.frydasignagesoftware.exception.ItemNotFoundException;
import com.deizon.frydasignagesoftware.model.AssetEntry;
import com.deizon.frydasignagesoftware.model.asset.Asset;
import com.deizon.frydasignagesoftware.model.style.Style;
import com.deizon.frydasignagesoftware.repository.AssetRepository;
import com.deizon.frydasignagesoftware.repository.StyleRepository;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AssetEntryResolver implements GraphQLResolver<AssetEntry> {

    private final AssetRepository assetRepository;
    private final StyleRepository styleRepository;

    public Asset getAsset(AssetEntry assetEntry) {
        return assetRepository
                .findById(assetEntry.getAsset())
                .orElseThrow(() -> new ItemNotFoundException(Asset.class));
    }

    public Style getAnimationIn(AssetEntry assetEntry) {
        if (assetEntry.getAnimationIn() == null) return null;

        return styleRepository
                .findById(assetEntry.getAnimationIn())
                .orElseThrow(() -> new ItemNotFoundException(Style.class));
    }

    public Style getAnimationOut(AssetEntry assetEntry) {
        if (assetEntry.getAnimationOut() == null) return null;

        return styleRepository
                .findById(assetEntry.getAnimationOut())
                .orElseThrow(() -> new ItemNotFoundException(Style.class));
    }
}
