/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.service;

import com.deizon.frydasignagesoftware.model.AssetEntry;
import com.deizon.frydasignagesoftware.model.asset.Asset;
import com.deizon.frydasignagesoftware.model.asset.FindAssetInput;
import com.deizon.frydasignagesoftware.model.assetlist.*;
import com.deizon.frydasignagesoftware.repository.AssetListRepository;
import com.deizon.frydasignagesoftware.repository.AssetRepository;
import com.deizon.services.exception.ItemNotFoundException;
import com.deizon.services.model.Validity;
import com.deizon.services.service.BaseService;
import com.deizon.services.util.EntityBuilder;
import com.deizon.services.util.ExampleBuilder;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class AssetListService
        extends BaseService<
                AssetList,
                CreateAssetListInput,
                UpdateAssetListInput,
                FindAssetListInput,
                AssetListRepository> {

    private final AssetService assetService;

    public AssetListService(AssetListRepository repository, AssetService assetService) {
        super(AssetList.class, repository, CreateAssetListInput.class, UpdateAssetListInput.class);

        this.assetService = assetService;
    }

    @Override
    protected Example<AssetList> createExample(FindAssetListInput input) {
        final AssetList data = new AssetList();
        return new ExampleBuilder<>(data)
                .exact()
                .stringField("id", input::getId, data::setId)
                .stringField("name", input::getName, data::setName)
                .stringField("type", input::getType, data::setType)
                .booleanField("prioritized", input::getPrioritized, data::setPrioritized)
                .booleanField("enabled", input::getEnabled, data::setEnabled)
                .booleanField("deleted", () -> false, data::setDeleted)
                .create();
    }

    @Override
    protected AssetList processData(AssetList entity, UpdateAssetListInput data) {
        return super.processData(
                new EntityBuilder<>(entity)
                        .stringField(data::getName, entity::setName)
                        .stringField(data::getType, entity::setType)
                        .booleanField(data::getPrioritized, entity::setPrioritized)
                        .booleanField(data::getEnabled, entity::setEnabled)
                        .stringField(data::getAnimationIn, entity::setAnimationIn)
                        .stringField(data::getAnimationOut, entity::setAnimationOut)
                        .objectField(data::getValidity, (val) -> entity.setValidity((Validity) val))
                        .getEntity(),
                data);
    }

    public AssetList assignAssetToAssetList(String id, AssetAssignInput data) {
        // TODO potřeba dodělat i update metodu a ta bude podle ID assignu editovat
        final AssetList assetList =
                this.repository
                        .findById(id)
                        .orElseThrow(() -> new ItemNotFoundException(AssetList.class));
        assetList.setUpdateDate(Instant.now());

        if (assetList.getAssets() == null) {
            assetList.setAssets(new ArrayList<>());
        }

        final List<String> assets = new ArrayList<>();
        if (data.getAsset().startsWith("dir")) {
            final FindAssetInput input = new FindAssetInput();
            input.setDirectory(data.getAsset().substring(4));
            assets.addAll(((List<Asset>) this.assetService.findAll(input))
                    .stream()
                    .map(Asset::getId)
                    .collect(Collectors.toList()));
        } else {
            assets.add(data.getAsset());
        }

        final Validity validity;
        if (data.getValidity() != null) {
            validity = data.getValidity();
        } else {
            validity = new Validity();
            validity.setEnabled(false);
        }

        assets.forEach(item -> {
            final AssetEntry assetEntry = new AssetEntry();
            assetEntry.setPosition(data.getPosition());
            assetEntry.setAsset(item);
            assetEntry.setValidity(validity);
            assetEntry.setAnimationIn(data.getAnimationIn());
            assetEntry.setAnimationOut(data.getAnimationOut());
            assetEntry.setShowTime(data.getShowTime());

            assetList.getAssets().add(assetEntry);
        });

        return this.repository.save(assetList);
    }

    public AssetList removeAssetFromAssetList(String id, String assetEntry) {
        final AssetList assetList =
                this.repository
                        .findById(id)
                        .orElseThrow(() -> new ItemNotFoundException(AssetList.class));
        assetList.setUpdateDate(Instant.now());

        assetList.getAssets().removeIf(value -> (value.getId().equals(assetEntry)));

        return this.repository.save(assetList);
    }
}
