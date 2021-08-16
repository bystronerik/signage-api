/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.resolver.entity;

import com.deizon.frydasignagesoftware.model.alert.Alert;
import com.deizon.frydasignagesoftware.model.assetlist.AssetList;
import com.deizon.frydasignagesoftware.model.group.Group;
import com.deizon.frydasignagesoftware.repository.AlertRepository;
import com.deizon.frydasignagesoftware.repository.AssetListRepository;
import com.deizon.services.exception.ItemNotFoundException;
import com.deizon.services.resolver.BaseResolver;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GroupEntityResolver extends BaseResolver<Group> {

    private final AssetListRepository assetListRepository;
    private final AlertRepository alertRepository;

    public CompletableFuture<Iterable<AssetList>> getAssetLists(Group group) {
        return this.processAsync(
                () -> {
                    if (group.getAssetLists() == null) return new ArrayList<>();

                    return assetListRepository.findAllById(group.getAssetLists());
                });
    }

    public CompletableFuture<Alert> getAlert(Group group) {
        return this.processAsync(
                () -> {
                    if (group.getAlert() == null) return null;

                    return alertRepository
                            .findById(group.getAlert())
                            .orElseThrow(() -> new ItemNotFoundException(Alert.class));
                });
    }
}
