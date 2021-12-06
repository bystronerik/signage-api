package com.deizon.frydasignagesoftware.resolver;

import com.deizon.frydasignagesoftware.exception.MissingDeployDataException;
import com.deizon.frydasignagesoftware.model.AssetEntry;
import com.deizon.frydasignagesoftware.model.alert.Alert;
import com.deizon.frydasignagesoftware.model.asset.Asset;
import com.deizon.frydasignagesoftware.model.assetlist.AssetList;
import com.deizon.frydasignagesoftware.model.deploydata.AlertData;
import com.deizon.frydasignagesoftware.model.deploydata.AssetData;
import com.deizon.frydasignagesoftware.model.deploydata.DeployData;
import com.deizon.frydasignagesoftware.model.deploydata.PlayerData;
import com.deizon.frydasignagesoftware.model.group.Group;
import com.deizon.frydasignagesoftware.model.player.Player;
import com.deizon.frydasignagesoftware.model.style.Style;
import com.deizon.frydasignagesoftware.publisher.DeployDataPublisher;
import com.deizon.frydasignagesoftware.repository.*;
import com.deizon.services.exception.ItemNotFoundException;
import com.deizon.services.resolver.BaseResolver;
import graphql.kickstart.tools.GraphQLSubscriptionResolver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.reactivestreams.Publisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeployDataResolver extends BaseResolver implements GraphQLSubscriptionResolver {

    private final DeployDataRepository deployDataRepository;
    private final PlayerRepository playerRepository;
    private final GroupRepository groupRepository;
    private final AssetListRepository assetListRepository;
    private final AlertRepository alertRepository;
    private final StyleRepository styleRepository;
    private final PlayerDataRepository playerDataRepository;
    private final DeployDataPublisher deployDataPublisher;
    private final AssetRepository assetRepository;

    @PreAuthorize("isAnonymous() || isAuthenticated()")
    public DeployData deployInfo() {
        return this.deployDataRepository.findAll().stream()
                .findFirst()
                .orElseThrow(MissingDeployDataException::new);
    }

    public Publisher<DeployData> deployInfoSync() {
        return this.deployDataPublisher.getPublisher();
    }

    @PreAuthorize("isAnonymous() || isAuthenticated()")
    public PlayerData syncPlayer(String token) {
        final DeployData deployInfo = deployInfo();

        final String groupId;
        if (token.startsWith("GID-")) {
            groupId = token.replace("GID-", "");
        } else {
            groupId =
                    playerRepository
                            .findByToken(token)
                            .orElseThrow(() -> new ItemNotFoundException(Player.class))
                            .getGroup();
        }

        final Group group =
                groupRepository
                        .findById(groupId)
                        .orElseThrow(() -> new ItemNotFoundException(Group.class));

        return playerDataRepository
                .findByVersion(group.getId(), deployInfo.getVersionHash())
                .orElseThrow(
                        () ->
                                new ItemNotFoundException(
                                        "Can not find player data, run deploy first!"));
    }

    public DeployData deploy() {
        final DeployData deployData =
                deployDataRepository.findAll().stream().findFirst().orElseGet(DeployData::new);

        deployData.setVersionHash(RandomStringUtils.random(20, true, true));

        this.groupRepository
                .findAll()
                .forEach(
                        group -> {
                            this.playerDataRepository.save(this.processGroup(deployData, group));
                        });

        this.deployDataRepository.save(deployData);
        this.deployDataPublisher.publish(deployData);

        return deployData;
    }

    private PlayerData processGroup(DeployData deployData, Group group) {
        final List<AssetData> assets = new ArrayList<>();
        final List<AssetData> priorityAssets = new ArrayList<>();

        final Map<String, String> stylesIds = new HashMap<>();
        final StringBuilder styles = new StringBuilder();

        final StylesManager stylesManager =
                value -> {
                    if (value == null) {
                        return null;
                    }

                    if (stylesIds.containsKey(value)) {
                        return stylesIds.get(value);
                    }
                    final Style style =
                            this.styleRepository
                                    .findById(value)
                                    .orElseThrow(() -> new ItemNotFoundException(Style.class));

                    if (style.getValueType().equals(Style.ValueType.PURE_CSS)) {
                        styles.append(".")
                                .append(style.getId())
                                .append("{")
                                .append(value)
                                .append("}");
                        stylesIds.put(value, style.getId());
                        return style.getId();
                    } else {
                        stylesIds.put(value, style.getValue());
                        return style.getValue();
                    }
                };

        if (group.getAssetLists() != null && !group.getAssetLists().isEmpty()) {
            assetListRepository
                    .findAllById(group.getAssetLists(), true)
                    .forEach(
                            playlist -> {
                                this.processAssetList(
                                        playlist, stylesManager, priorityAssets, assets);
                            });
        }

        AlertData alert = null;
        if (group.getAlert() != null) {
            final Alert data =
                    alertRepository
                            .findById(group.getAlert())
                            .orElseThrow(() -> new ItemNotFoundException(Alert.class));
            alert = this.processAlert(data, stylesManager);
        }

        return new PlayerData(
                group.getId(),
                deployData.getVersionHash(),
                assets,
                priorityAssets,
                alert,
                styles.toString());
    }

    private void processAssetList(
            AssetList playlist,
            StylesManager stylesManager,
            List<AssetData> priorityAssets,
            List<AssetData> assets) {
        if (playlist.getValidity() != null && playlist.getValidity().getEnabled()) {
            if (playlist.getValidity().isInPast()) {
                return;
            }
        }

        playlist.getAssets()
                .forEach(
                        entry -> {
                            final AssetData data =
                                    this.processAsset(playlist, entry, stylesManager);
                            if (data == null) {
                                return;
                            }

                            if (playlist.getPrioritized()) {
                                priorityAssets.add(data);
                            } else {
                                assets.add(data);
                            }
                        });
    }

    private AssetData processAsset(
            AssetList playlist, AssetEntry input, StylesManager stylesManager) {
        final AssetData data = new AssetData();
        final Asset assetInput =
                this.assetRepository
                        .findById(input.getAsset())
                        .orElseThrow(() -> new ItemNotFoundException(Asset.class));

        data.setId(input.getId());

        if (input.getValidity() != null && input.getValidity().getEnabled()) {
            if (input.getValidity().isInPast()) {
                return null;
            }
        }

        if (input.getValidity() != null && input.getValidity().getEnabled()) {
            if (playlist.getValidity().getFrom().isAfter(input.getValidity().getFrom())) {
                input.getValidity().setFrom(playlist.getValidity().getFrom());
            }

            if (playlist.getValidity().getTo().isBefore(input.getValidity().getTo())) {
                input.getValidity().setTo(playlist.getValidity().getTo());
            }
            data.setValidity(input.getValidity());
        } else {
            data.setValidity(playlist.getValidity());
        }

        if (assetInput.getValidity() != null) {
            if (assetInput.getValidity().getFrom().isAfter(data.getValidity().getFrom())) {
                data.getValidity().setFrom(assetInput.getValidity().getFrom());
            }

            if (assetInput.getValidity().getTo().isBefore(data.getValidity().getTo())) {
                data.getValidity().setTo(assetInput.getValidity().getTo());
            }
        }

        if (assetInput.getAlert() != null) {
            data.setAlert(
                    this.processAlert(
                            this.alertRepository
                                    .findById(assetInput.getAlert())
                                    .orElseThrow(() -> new ItemNotFoundException(Alert.class)),
                            stylesManager));
        }

        data.setPath(assetInput.getPath());
        data.setType(assetInput.getType());
        data.setShowTime(
                input.getShowTime() != null && input.getShowTime() > 0
                        ? input.getShowTime()
                        : (assetInput.getShowTime() != null && assetInput.getShowTime() > 0
                                ? assetInput.getShowTime()
                                : 20));
        data.setAnimationIn(stylesManager.addStyles(assetInput.getAnimationIn()));
        data.setAnimationOut(stylesManager.addStyles(assetInput.getAnimationOut()));

        return data;
    }

    private AlertData processAlert(Alert input, StylesManager stylesManager) {
        final AlertData data = new AlertData();

        data.setId(input.getId());
        data.setValidity(input.getValidity());
        data.setRunning(input.getRunning());
        data.setPosition(input.getPosition());
        data.setValue(input.getValue());

        data.setBackground(stylesManager.addStyles(input.getBackground()));
        data.setBorders(stylesManager.addStyles(input.getBorders()));
        data.setHeight(stylesManager.addStyles(input.getHeight()));
        data.setTextColor(stylesManager.addStyles(input.getTextColor()));
        data.setTextPosition(stylesManager.addStyles(input.getTextPosition()));
        data.setTextSize(stylesManager.addStyles(input.getTextSize()));

        return data;
    }

    interface StylesManager {
        String addStyles(String value);
    }
}
