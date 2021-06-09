package com.deizon.frydasignagesoftware.resolver;

import com.deizon.frydasignagesoftware.exception.AlertNotFoundException;
import com.deizon.frydasignagesoftware.exception.StyleNotFoundException;
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
public class AssetResolver implements GraphQLResolver<Asset> {

    private final AssetListRepository assetListRepository;
    private final StyleRepository styleRepository;
    private final AlertRepository alertRepository;

    public Iterable<AssetList> getAssetLists(Asset asset) {
        return assetListRepository.findAllByAsset(asset.getId());
    }

    public Alert getAlert(Asset asset) {
        if (asset.getAlert() == null) return null;

        return alertRepository.findById(asset.getAlert()).orElseThrow(AlertNotFoundException::new);
    }

    public Style getAnimationIn(Asset asset) {
        if (asset.getAnimationIn() == null) return null;

        return styleRepository
                .findById(asset.getAnimationIn())
                .orElseThrow(StyleNotFoundException::new);
    }

    public Style getAnimationOut(Asset asset) {
        if (asset.getAnimationOut() == null) return null;

        return styleRepository
                .findById(asset.getAnimationOut())
                .orElseThrow(StyleNotFoundException::new);
    }

    public String getType(Asset asset) {
        return asset.getType().getName();
    }
}
