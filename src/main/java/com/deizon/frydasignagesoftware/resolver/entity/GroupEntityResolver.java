/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.resolver.entity;

import com.deizon.frydasignagesoftware.model.alert.Alert;
import com.deizon.frydasignagesoftware.model.assetlist.AssetList;
import com.deizon.frydasignagesoftware.model.group.Group;
import com.deizon.frydasignagesoftware.repository.AlertRepository;
import com.deizon.frydasignagesoftware.repository.AssetListRepository;
import com.deizon.services.exception.ItemNotFoundException;
import graphql.kickstart.tools.GraphQLResolver;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GroupEntityResolver implements GraphQLResolver<Group> {

    private final AssetListRepository assetListRepository;
    private final AlertRepository alertRepository;

    public Iterable<AssetList> getAssetLists(Group group) {
        if (group.getAssetLists() == null) return new ArrayList<>();

        return assetListRepository.findAllById(group.getAssetLists());
    }

    public Alert getAlert(Group group) {
        if (group.getAlert() == null) return null;

        return alertRepository
                .findById(group.getAlert())
                .orElseThrow(() -> new ItemNotFoundException(Alert.class));
    }
}
