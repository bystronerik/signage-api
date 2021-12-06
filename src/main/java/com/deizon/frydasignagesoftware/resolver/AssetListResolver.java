package com.deizon.frydasignagesoftware.resolver;

import com.deizon.frydasignagesoftware.model.assetlist.*;
import com.deizon.frydasignagesoftware.service.AssetListService;
import com.deizon.services.resolver.BaseResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@PreAuthorize("isAuthenticated()")
public class AssetListResolver extends BaseResolver {

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
