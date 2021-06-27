/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.resolver;

import com.deizon.frydasignagesoftware.exception.ItemNotFoundException;
import com.deizon.frydasignagesoftware.exception.MissingDeployDataException;
import com.deizon.frydasignagesoftware.model.AssetEntry;
import com.deizon.frydasignagesoftware.model.alert.Alert;
import com.deizon.frydasignagesoftware.model.deploydata.DeployData;
import com.deizon.frydasignagesoftware.model.deploydata.PlayerData;
import com.deizon.frydasignagesoftware.model.group.Group;
import com.deizon.frydasignagesoftware.model.player.Player;
import com.deizon.frydasignagesoftware.repository.*;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@PreAuthorize("isAuthenticated()")
public class DeployDataResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final DeployDataRepository deployDataRepository;
    private final PlayerRepository playerRepository;
    private final GroupRepository groupRepository;
    private final AssetListRepository assetListRepository;
    private final AlertRepository alertRepository;
    private final StyleRepository styleRepository;

    @PreAuthorize("isAnonymous() || isAuthenticated()")
    public DeployData deployInfo() {
        return deployDataRepository.findAll().stream()
                .findFirst()
                .orElseThrow(MissingDeployDataException::new);
    }

    @PreAuthorize("isAnonymous() || isAuthenticated()")
    public PlayerData syncPlayer(String token) {
        final List<AssetEntry> assets = new ArrayList<>();
        final List<AssetEntry> priorityAssets = new ArrayList<>();

        final Player player =
                playerRepository
                        .findByToken(token)
                        .orElseThrow(() -> new ItemNotFoundException(Player.class));
        final Group group =
                groupRepository
                        .findById(player.getGroup())
                        .orElseThrow(() -> new ItemNotFoundException(Group.class));

        if (group.getAssetLists() != null && !group.getAssetLists().isEmpty()) {
            assetListRepository
                    .findAllById(group.getAssetLists(), true)
                    .forEach(
                            playlist -> {
                                if (playlist.getValidity() != null
                                        && playlist.getValidity().getEnabled()) {
                                    if (playlist.getValidity().isInPast()) {
                                        return;
                                    }
                                }

                                playlist.getAssets()
                                        .forEach(
                                                entry -> {
                                                    if (entry.getValidity() != null
                                                            && entry.getValidity().getEnabled()) {
                                                        if (entry.getValidity().isInPast()) {
                                                            return;
                                                        }

                                                        if (entry.getValidity().getEnabled()) {
                                                            if (playlist.getValidity()
                                                                    .getFrom()
                                                                    .isAfter(
                                                                            entry.getValidity()
                                                                                    .getFrom())) {
                                                                entry.getValidity()
                                                                        .setFrom(
                                                                                playlist.getValidity()
                                                                                        .getFrom());
                                                            }

                                                            if (playlist.getValidity()
                                                                    .getTo()
                                                                    .isBefore(
                                                                            entry.getValidity()
                                                                                    .getTo())) {
                                                                entry.getValidity()
                                                                        .setTo(
                                                                                playlist.getValidity()
                                                                                        .getTo());
                                                            }
                                                        } else {
                                                            entry.setValidity(
                                                                    playlist.getValidity());
                                                        }
                                                    }

                                                    if (playlist.getPrioritized()) {
                                                        priorityAssets.add(entry);
                                                    } else {
                                                        assets.add(entry);
                                                    }
                                                });
                            });
        }

        Alert alert = null;
        if (group.getAlert() != null) {
            alert =
                    alertRepository
                            .findById(group.getAlert())
                            .orElseThrow(() -> new ItemNotFoundException(Alert.class));
        }

        return new PlayerData(assets, priorityAssets, alert, styleRepository.findAll());
    }

    public DeployData deploy() {
        final DeployData deployData =
                deployDataRepository.findAll().stream().findFirst().orElseGet(DeployData::new);

        deployData.setVersionHash(RandomStringUtils.random(20, true, true));

        return deployDataRepository.save(deployData);
    }
}