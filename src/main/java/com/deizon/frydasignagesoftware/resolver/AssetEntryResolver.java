package com.deizon.frydasignagesoftware.resolver;

import com.deizon.frydasignagesoftware.exception.AssetNotFoundException;
import com.deizon.frydasignagesoftware.model.AssetEntry;
import com.deizon.frydasignagesoftware.model.asset.Asset;
import com.deizon.frydasignagesoftware.repository.AssetRepository;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AssetEntryResolver implements GraphQLResolver<AssetEntry> {

    private final AssetRepository assetRepository;

    public Asset getAsset(AssetEntry assetEntry) {
        return assetRepository
                .findById(assetEntry.getAsset())
                .orElseThrow(AssetNotFoundException::new);
    }
}
