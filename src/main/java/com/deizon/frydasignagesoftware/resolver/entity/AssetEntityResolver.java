/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.resolver.entity;

import com.deizon.frydasignagesoftware.exception.ItemNotFoundException;
import com.deizon.frydasignagesoftware.model.alert.Alert;
import com.deizon.frydasignagesoftware.model.asset.Asset;
import com.deizon.frydasignagesoftware.model.assetlist.AssetList;
import com.deizon.frydasignagesoftware.model.style.Style;
import com.deizon.frydasignagesoftware.repository.AlertRepository;
import com.deizon.frydasignagesoftware.repository.AssetListRepository;
import com.deizon.frydasignagesoftware.repository.StyleRepository;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AssetEntityResolver implements GraphQLResolver<Asset> {

    private final AssetListRepository assetListRepository;
    private final StyleRepository styleRepository;
    private final AlertRepository alertRepository;

    public Iterable<AssetList> getAssetLists(Asset asset) {
        return assetListRepository.findAllByAsset(asset.getId());
    }

    public Alert getAlert(Asset asset) {
        if (asset.getAlert() == null) return null;

        return alertRepository
                .findById(asset.getAlert())
                .orElseThrow(() -> new ItemNotFoundException(Alert.class));
    }

    public Style getAnimationIn(Asset asset) {
        if (asset.getAnimationIn() == null) return null;

        return styleRepository
                .findById(asset.getAnimationIn())
                .orElseThrow(() -> new ItemNotFoundException(Style.class));
    }

    public Style getAnimationOut(Asset asset) {
        if (asset.getAnimationOut() == null) return null;

        return styleRepository
                .findById(asset.getAnimationOut())
                .orElseThrow(() -> new ItemNotFoundException(Style.class));
    }

    public String getType(Asset asset) {
        return asset.getType().getName();
    }
}