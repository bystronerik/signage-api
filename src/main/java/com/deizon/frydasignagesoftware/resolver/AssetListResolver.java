package com.deizon.frydasignagesoftware.resolver;

import com.deizon.frydasignagesoftware.exception.StyleNotFoundException;
import com.deizon.frydasignagesoftware.model.assetlist.AssetList;
import com.deizon.frydasignagesoftware.model.style.Style;
import com.deizon.frydasignagesoftware.repository.StyleRepository;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AssetListResolver implements GraphQLResolver<AssetList> {

    private final StyleRepository styleRepository;

    public Style getAnimationIn(AssetList assetList) {
        return styleRepository
                .findById(assetList.getAnimationIn())
                .orElseThrow(StyleNotFoundException::new);
    }

    public Style getAnimationOut(AssetList assetList) {
        return styleRepository
                .findById(assetList.getAnimationOut())
                .orElseThrow(StyleNotFoundException::new);
    }
}
