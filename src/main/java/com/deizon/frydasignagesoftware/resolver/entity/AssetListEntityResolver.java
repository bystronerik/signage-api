/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.resolver.entity;

import com.deizon.frydasignagesoftware.model.assetlist.AssetList;
import com.deizon.frydasignagesoftware.model.style.Style;
import com.deizon.frydasignagesoftware.repository.StyleRepository;
import com.deizon.services.exception.ItemNotFoundException;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AssetListEntityResolver implements GraphQLResolver<AssetList> {

    private final StyleRepository styleRepository;

    public Style getAnimationIn(AssetList assetList) {
        return styleRepository
                .findById(assetList.getAnimationIn())
                .orElseThrow(() -> new ItemNotFoundException(Style.class));
    }

    public Style getAnimationOut(AssetList assetList) {
        return styleRepository
                .findById(assetList.getAnimationOut())
                .orElseThrow(() -> new ItemNotFoundException(Style.class));
    }
}
