/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.resolver;

import com.deizon.frydasignagesoftware.exception.ItemNotFoundException;
import com.deizon.frydasignagesoftware.model.AssetEntry;
import com.deizon.services.model.Validity;
import com.deizon.frydasignagesoftware.model.assetlist.*;
import com.deizon.frydasignagesoftware.repository.*;
import com.deizon.frydasignagesoftware.service.AssetListService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import java.time.Instant;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@PreAuthorize("isAuthenticated()")
public class AssetListResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final AssetListRepository assetListRepository;
    private final AssetListService service;

    public Iterable<AssetList> findAllAssetLists(FindAssetListInput input) {
        return this.service.findAll(input);
    }

    public AssetList findAssetList(FindAssetListInput input) {
        return this.service.find(input);
    }

    public AssetList createAssetList(CreateAssetListInput data) {
        return this.service.create(data);
    }

    public AssetList updateAssetList(String id, UpdateAssetListInput data) {
        return this.service.update(id, data);
    }

    public AssetList assignAssetToAssetList(String id, AssetAssignInput data) {
        return this.service.assignAssetToAssetList(id, data);
    }

    public AssetList removeAssetFromAssetList(String id, String assetEntry) {
        return this.service.removeAssetFromAssetList(id, assetEntry);
    }

    public AssetList deleteAssetList(String id) {
        return this.service.delete(id);
    }

    public boolean totalDeleteAssetList(String id) {
        return this.service.totalDelete(id);
    }
}
