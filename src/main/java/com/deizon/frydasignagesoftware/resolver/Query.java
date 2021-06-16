/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.resolver;

import com.deizon.frydasignagesoftware.exception.ItemNotFoundException;
import com.deizon.frydasignagesoftware.exception.MissingDeployDataException;
import com.deizon.frydasignagesoftware.model.AssetEntry;
import com.deizon.frydasignagesoftware.model.alert.Alert;
import com.deizon.frydasignagesoftware.model.alert.FindAlertInput;
import com.deizon.frydasignagesoftware.model.asset.Asset;
import com.deizon.frydasignagesoftware.model.asset.FindAssetInput;
import com.deizon.frydasignagesoftware.model.assetlist.AssetList;
import com.deizon.frydasignagesoftware.model.assetlist.FindAssetListInput;
import com.deizon.frydasignagesoftware.model.deploydata.DeployData;
import com.deizon.frydasignagesoftware.model.deploydata.PlayerData;
import com.deizon.frydasignagesoftware.model.directory.Directory;
import com.deizon.frydasignagesoftware.model.directory.FindDirectoryInput;
import com.deizon.frydasignagesoftware.model.group.FindGroupInput;
import com.deizon.frydasignagesoftware.model.group.Group;
import com.deizon.frydasignagesoftware.model.player.FindPlayerInput;
import com.deizon.frydasignagesoftware.model.player.Player;
import com.deizon.frydasignagesoftware.model.style.FindStyleInput;
import com.deizon.frydasignagesoftware.model.style.Style;
import com.deizon.frydasignagesoftware.model.tag.FindTagInput;
import com.deizon.frydasignagesoftware.model.tag.Tag;
import com.deizon.frydasignagesoftware.model.user.FindUserInput;
import com.deizon.frydasignagesoftware.model.user.User;
import com.deizon.frydasignagesoftware.repository.*;
import graphql.kickstart.tools.GraphQLQueryResolver;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@SuppressWarnings("unused")
@RequiredArgsConstructor
@Component
@PreAuthorize("isAuthenticated()")
public class Query implements GraphQLQueryResolver {

    private final AssetRepository assetRepository;
    private final GroupRepository groupRepository;
    private final PlayerRepository playerRepository;
    private final AssetListRepository assetListRepository;
    private final UserRepository userRepository;
    private final DeployDataRepository deployDataRepository;
    private final StyleRepository styleRepository;
    private final AlertRepository alertRepository;
    private final DirectoryRepository directoryRepository;
    private final TagRepository tagRepository;

    @PreAuthorize("hasAuthority('ADMIN')")
    public Iterable<User> findAllUsers(FindUserInput data) {
        return userRepository.findAll(User.createExample(data));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public User findUser(FindUserInput data) throws Throwable {
        return userRepository
                .findOne(User.createExample(data))
                .orElseThrow(() -> new ItemNotFoundException(User.class));
    }

    public Iterable<Asset> findAllAssets(FindAssetInput data) {
        return assetRepository.findAll(Asset.createExample(data));
    }

    public Asset findAsset(FindAssetInput data) throws Throwable {
        return assetRepository
                .findOne(Asset.createExample(data))
                .orElseThrow(() -> new ItemNotFoundException(Asset.class));
    }

    public Iterable<Group> findAllGroups(FindGroupInput data) {
        return groupRepository.findAll(Group.createExample(data));
    }

    public Group findGroup(FindGroupInput data) throws Throwable {
        return groupRepository
                .findOne(Group.createExample(data))
                .orElseThrow(() -> new ItemNotFoundException(Group.class));
    }

    public Iterable<Player> findAllPlayers(FindPlayerInput data) {
        return playerRepository.findAll(Player.createExample(data));
    }

    public Player findPlayer(FindPlayerInput data) throws Throwable {
        return playerRepository
                .findOne(Player.createExample(data))
                .orElseThrow(() -> new ItemNotFoundException(Player.class));
    }

    public Iterable<AssetList> findAllAssetLists(FindAssetListInput data) {
        return assetListRepository.findAll(AssetList.createExample(data));
    }

    public AssetList findAssetList(FindAssetListInput data) throws Throwable {
        return assetListRepository
                .findOne(AssetList.createExample(data))
                .orElseThrow(() -> new ItemNotFoundException(AssetList.class));
    }

    public Iterable<Style> findAllStyles(FindStyleInput data) {
        return styleRepository.findAll(Style.createExample(data));
    }

    public Style findStyle(FindStyleInput data) throws Throwable {
        return styleRepository
                .findOne(Style.createExample(data))
                .orElseThrow(() -> new ItemNotFoundException(Style.class));
    }

    public Iterable<Alert> findAllAlerts(FindAlertInput data) {
        return alertRepository.findAll(Alert.createExample(data));
    }

    public Alert findAlert(FindAlertInput data) throws Throwable {
        return alertRepository
                .findOne(Alert.createExample(data))
                .orElseThrow(() -> new ItemNotFoundException(Alert.class));
    }

    public Iterable<Directory> findAllDirectories(FindDirectoryInput data) {
        return directoryRepository.findAll(Directory.createExample(data));
    }

    public Directory findDirectory(FindDirectoryInput data) throws Throwable {
        return directoryRepository
                .findOne(Directory.createExample(data))
                .orElseThrow(() -> new ItemNotFoundException(Directory.class));
    }

    public Iterable<Tag> findAllTags(FindTagInput data) {
        return tagRepository.findAll(Tag.createExample(data));
    }

    public Tag findTag(FindTagInput data) throws Throwable {
        return tagRepository
                .findOne(Tag.createExample(data))
                .orElseThrow(() -> new ItemNotFoundException(Tag.class));
    }

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
}
