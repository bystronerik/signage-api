package com.deizon.frydasignagesoftware.resolver;

import com.deizon.frydasignagesoftware.exception.*;
import com.deizon.frydasignagesoftware.model.AssetEntry;
import com.deizon.frydasignagesoftware.model.FileUpload;
import com.deizon.frydasignagesoftware.model.Validity;
import com.deizon.frydasignagesoftware.model.alert.Alert;
import com.deizon.frydasignagesoftware.model.alert.CreateAlertInput;
import com.deizon.frydasignagesoftware.model.alert.UpdateAlertInput;
import com.deizon.frydasignagesoftware.model.asset.Asset;
import com.deizon.frydasignagesoftware.model.asset.CreateAssetInput;
import com.deizon.frydasignagesoftware.model.asset.UpdateAssetInput;
import com.deizon.frydasignagesoftware.model.assetlist.AssetAssignInput;
import com.deizon.frydasignagesoftware.model.assetlist.AssetList;
import com.deizon.frydasignagesoftware.model.assetlist.CreateAssetListInput;
import com.deizon.frydasignagesoftware.model.assetlist.UpdateAssetListInput;
import com.deizon.frydasignagesoftware.model.auth.LoginDetails;
import com.deizon.frydasignagesoftware.model.deploydata.DeployData;
import com.deizon.frydasignagesoftware.model.group.CreateGroupInput;
import com.deizon.frydasignagesoftware.model.group.Group;
import com.deizon.frydasignagesoftware.model.group.UpdateGroupInput;
import com.deizon.frydasignagesoftware.model.player.CreatePlayerInput;
import com.deizon.frydasignagesoftware.model.player.Player;
import com.deizon.frydasignagesoftware.model.player.UpdatePlayerInput;
import com.deizon.frydasignagesoftware.model.style.CreateStyleInput;
import com.deizon.frydasignagesoftware.model.style.Style;
import com.deizon.frydasignagesoftware.model.style.UpdateStyleInput;
import com.deizon.frydasignagesoftware.model.user.CreateUserInput;
import com.deizon.frydasignagesoftware.model.user.UpdateUserInput;
import com.deizon.frydasignagesoftware.model.user.User;
import com.deizon.frydasignagesoftware.repository.*;
import com.deizon.frydasignagesoftware.security.UserService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@PreAuthorize("isAuthenticated()")
public class Mutation implements GraphQLMutationResolver {

    private final AssetRepository assetRepository;
    private final GroupRepository groupRepository;
    private final PlayerRepository playerRepository;
    private final AssetListRepository assetListRepository;
    private final UserRepository userRepository;
    private final DeployDataRepository deployDataRepository;
    private final StyleRepository styleRepository;
    private final AlertRepository alertRepository;

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final ResourceLoader resourceLoader;

    private final Environment environment;

    @PreAuthorize("isAnonymous()")
    public LoginDetails login(String username, String password, Boolean shortSession) {
        User user =
                userRepository.findByUsername(username).orElseThrow(BadCredentialsException::new);

        if (passwordEncoder.matches(password, user.getPassword())) {
            return new LoginDetails(user, userService.getTokenObject(user, shortSession));
        } else {
            throw new BadCredentialsException();
        }
    }

    public AssetList createAssetList(CreateAssetListInput data) {
        final AssetList assetList = new AssetList();
        assetList.setCreateDate(Instant.now());
        assetList.setDeleted(false);
        assetList.setName(data.getName());
        assetList.setType(data.getType());

        if (data.getAnimationIn() != null) assetList.setAnimationIn(data.getAnimationIn());

        if (data.getAnimationOut() != null) assetList.setAnimationOut(data.getAnimationOut());

        if (data.getEnabled() != null) {
            assetList.setEnabled(data.getEnabled());
        } else {
            assetList.setEnabled(true);
        }

        if (data.getValidityEnabled() != null
                && data.getValidFrom() != null
                && data.getValidTo() != null)
            assetList.setValidity(
                    new Validity(
                            data.getValidityEnabled(), data.getValidFrom(), data.getValidTo()));

        return assetListRepository.save(assetList);
    }

    public AssetList updateAssetList(String id, UpdateAssetListInput data) {
        final AssetList assetList =
                assetListRepository.findById(id).orElseThrow(AssetListNotFoundException::new);
        assetList.setUpdateDate(Instant.now());

        if (data.getName() != null) assetList.setName(data.getName());

        if (data.getAnimationIn() != null) assetList.setAnimationIn(data.getAnimationIn());

        if (data.getAnimationOut() != null) assetList.setAnimationOut(data.getAnimationOut());

        if (data.getType() != null) assetList.setType(data.getType());

        if (data.getEnabled() != null) assetList.setEnabled(data.getEnabled());

        if (data.getValidityEnabled() != null
                && data.getValidFrom() != null
                && data.getValidTo() != null)
            assetList.setValidity(
                    new Validity(
                            data.getValidityEnabled(), data.getValidFrom(), data.getValidTo()));

        return assetListRepository.save(assetList);
    }

    public AssetList assignAssetToAssetList(String id, AssetAssignInput data) {
        final AssetList assetList =
                assetListRepository.findById(id).orElseThrow(AssetListNotFoundException::new);
        assetList.setUpdateDate(Instant.now());

        if (assetList.getAssets() == null) {
            assetList.setAssets(new ArrayList<>());
        } else {
            assetList.getAssets().removeIf(value -> (value.getAsset().equals(data.getAsset())));
        }

        final Validity validity = new Validity();
        validity.setEnabled(data.getValidityEnabled());
        validity.setFrom(data.getValidFrom());
        validity.setTo(data.getValidTo());

        final AssetEntry assetEntry = new AssetEntry();
        assetEntry.setAsset(data.getAsset());
        assetEntry.setValidity(validity);

        assetList.getAssets().add(assetEntry);

        return assetListRepository.save(assetList);
    }

    public AssetList removeAssetFromAssetList(String id, String asset) {
        final AssetList assetList =
                assetListRepository.findById(id).orElseThrow(AssetListNotFoundException::new);
        assetList.setUpdateDate(Instant.now());

        assetList.getAssets().removeIf(value -> (value.getAsset().equals(asset)));

        return assetListRepository.save(assetList);
    }

    public Boolean deleteAssetList(String id) {
        final AssetList assetList =
                assetListRepository.findById(id).orElseThrow(AssetListNotFoundException::new);
        assetList.setDeleted(true);
        assetList.setUpdateDate(Instant.now());

        assetListRepository.save(assetList);

        return true;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public User createUser(CreateUserInput data) {
        if (userRepository.findByUsername(data.getUsername()).isPresent())
            throw new UserAlreadyExistsException();

        final User user = new User();
        user.setCreateDate(Instant.now());
        user.setDeleted(false);

        user.setUsername(data.getUsername());
        user.setPassword(passwordEncoder.encode(data.getPassword()));

        if (data.getRole() == null) data.setRole(User.Role.USER);
        user.setRole(data.getRole());

        return userRepository.save(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public User updateUser(String id, UpdateUserInput data) {
        final User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        user.setUpdateDate(Instant.now());

        if (data.getUsername() != null) {
            if (!user.getUsername().equals(data.getUsername())) {
                if (userRepository.findByUsername(data.getUsername()).isPresent()) {
                    throw new UserAlreadyExistsException();
                }
            }

            user.setUsername(data.getUsername());
        }

        if (data.getPassword() != null)
            user.setPassword(passwordEncoder.encode(data.getPassword()));

        if (data.getRole() != null) user.setRole(data.getRole());

        return userRepository.save(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public Boolean deleteUser(String id) {
        final User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        user.setDeleted(true);
        user.setUpdateDate(Instant.now());

        userRepository.save(user);

        return true;
    }

    public Group createGroup(CreateGroupInput data) {
        final Group group = new Group();
        group.setCreateDate(Instant.now());
        group.setDeleted(false);

        group.setName(data.getName());

        if (data.getAssetLists() != null) group.setAssetLists(data.getAssetLists());

        if (data.getAlert() != null) group.setAlert(data.getAlert());

        return groupRepository.save(group);
    }

    public Group updateGroup(String id, UpdateGroupInput data) {
        final Group group = groupRepository.findById(id).orElseThrow(GroupNotFoundException::new);
        group.setUpdateDate(Instant.now());

        if (data.getName() != null) group.setName(data.getName());

        if (data.getAssetLists() != null) group.setAssetLists(data.getAssetLists());

        if (data.getAlert() != null) group.setAlert(data.getAlert());

        return groupRepository.save(group);
    }

    public Boolean deleteGroup(String id) {
        final Group group = groupRepository.findById(id).orElseThrow(GroupNotFoundException::new);
        group.setDeleted(true);
        group.setUpdateDate(Instant.now());

        groupRepository.save(group);

        return true;
    }

    public Style createStyle(CreateStyleInput data) {
        final Style style = new Style();
        style.setCreateDate(Instant.now());
        style.setDeleted(false);

        style.setName(data.getName());
        style.setType(data.getType());
        style.setValue(data.getValue());
        style.setValueType(data.getValueType());

        return styleRepository.save(style);
    }

    public Style updateStyle(String id, UpdateStyleInput data) {
        final Style style = styleRepository.findById(id).orElseThrow(StyleNotFoundException::new);
        style.setUpdateDate(Instant.now());

        if (data.getName() != null) style.setName(data.getName());

        if (data.getType() != null) style.setType(data.getType());

        if (data.getValue() != null) style.setValue(data.getValue());

        if (data.getValueType() != null) style.setValueType(data.getValueType());

        return styleRepository.save(style);
    }

    public Boolean deleteStyle(String id) {
        final Style style = styleRepository.findById(id).orElseThrow(StyleNotFoundException::new);
        style.setDeleted(true);
        style.setUpdateDate(Instant.now());

        styleRepository.save(style);

        return true;
    }

    public Alert createAlert(CreateAlertInput data) {
        final Alert alert = new Alert();
        alert.setCreateDate(Instant.now());
        alert.setDeleted(false);

        alert.setName(data.getName());
        alert.setPosition(data.getPosition());
        alert.setType(data.getType());
        alert.setValue(data.getValue());

        if (data.getBorders() != null) alert.setBorders(data.getBorders());

        if (data.getHeight() != null) alert.setHeight(data.getHeight());

        if (data.getBackground() != null) alert.setBackground(data.getBackground());

        if (data.getTextColor() != null) alert.setTextColor(data.getTextColor());

        if (data.getTextPosition() != null) alert.setTextPosition(data.getTextPosition());

        if (data.getTextSize() != null) alert.setTextSize(data.getTextSize());

        if (data.getValidityEnabled() != null
                && data.getValidFrom() != null
                && data.getValidTo() != null)
            alert.setValidity(
                    new Validity(
                            data.getValidityEnabled(), data.getValidFrom(), data.getValidTo()));

        return alertRepository.save(alert);
    }

    public Alert updateAlert(String id, UpdateAlertInput data) {
        final Alert alert = alertRepository.findById(id).orElseThrow(AlertNotFoundException::new);
        alert.setUpdateDate(Instant.now());

        if (data.getName() != null) alert.setName(data.getName());

        if (data.getPosition() != null) alert.setPosition(data.getPosition());

        if (data.getType() != null) alert.setType(data.getType());

        if (data.getValue() != null) alert.setValue(data.getValue());

        if (data.getBorders() != null) alert.setBorders(data.getBorders());

        if (data.getHeight() != null) alert.setHeight(data.getHeight());

        if (data.getBackground() != null) alert.setBackground(data.getBackground());

        if (data.getTextColor() != null) alert.setTextColor(data.getTextColor());

        if (data.getTextPosition() != null) alert.setTextPosition(data.getTextPosition());

        if (data.getTextSize() != null) alert.setTextSize(data.getTextSize());

        if (data.getValidityEnabled() != null
                && data.getValidFrom() != null
                && data.getValidTo() != null)
            alert.setValidity(
                    new Validity(
                            data.getValidityEnabled(), data.getValidFrom(), data.getValidTo()));

        return alertRepository.save(alert);
    }

    public Boolean deleteAlert(String id) {
        final Alert alert = alertRepository.findById(id).orElseThrow(AlertNotFoundException::new);
        alert.setDeleted(true);
        alert.setUpdateDate(Instant.now());

        alertRepository.save(alert);

        return true;
    }

    public Player createPlayer(CreatePlayerInput data) {
        final Player player = new Player();
        player.setCreateDate(Instant.now());
        player.setDeleted(false);

        player.setName(data.getName());
        player.setToken(data.getToken());

        if (data.getGroup() != null) player.setGroup(data.getGroup());

        return playerRepository.save(player);
    }

    public Player updatePlayer(String id, UpdatePlayerInput data) {
        final Player player =
                playerRepository.findById(id).orElseThrow(PlayerNotFoundException::new);
        player.setUpdateDate(Instant.now());

        if (data.getName() != null) player.setName(data.getName());

        if (data.getToken() != null) player.setToken(data.getToken());

        if (data.getGroup() != null) player.setGroup(data.getGroup());

        return playerRepository.save(player);
    }

    public Boolean deletePlayer(String id) {
        final Player player =
                playerRepository.findById(id).orElseThrow(PlayerNotFoundException::new);
        player.setDeleted(true);
        player.setUpdateDate(Instant.now());

        playerRepository.save(player);

        return true;
    }

    public DeployData deploy() {
        final DeployData deployData =
                deployDataRepository.findAll().stream().findFirst().orElseGet(DeployData::new);

        deployData.setVersionHash(RandomStringUtils.random(20, true, true));

        return deployDataRepository.save(deployData);
    }

    public Asset createAsset(CreateAssetInput data, FileUpload file) throws IOException {
        final Asset asset = new Asset();
        asset.setCreateDate(Instant.now());
        asset.setDeleted(false);

        asset.setName(data.getName());
        processAssetFile(asset, file);

        if (data.getShowTime() != null) {
            asset.setShowTime(data.getShowTime());
        }

        if (data.getValidityEnabled() != null
                && data.getValidFrom() != null
                && data.getValidTo() != null)
            asset.setValidity(
                    new Validity(
                            data.getValidityEnabled(), data.getValidFrom(), data.getValidTo()));

        if (data.getAnimationIn() != null) {
            asset.setAnimationIn(data.getAnimationIn());
        }

        if (data.getAnimationOut() != null) {
            asset.setAnimationOut(data.getAnimationOut());
        }

        return assetRepository.save(asset);
    }

    public Asset updateAsset(String id, UpdateAssetInput data, FileUpload file) throws IOException {
        final Asset asset = assetRepository.findById(id).orElseThrow(AssetNotFoundException::new);
        asset.setUpdateDate(Instant.now());

        if (data.getName() != null) asset.setName(data.getName());

        if (file != null) {
            processAssetFile(asset, file);
        }

        if (data.getShowTime() != null) {
            asset.setShowTime(data.getShowTime());
        }

        if (data.getValidityEnabled() != null
                && data.getValidFrom() != null
                && data.getValidTo() != null)
            asset.setValidity(
                    new Validity(
                            data.getValidityEnabled(), data.getValidFrom(), data.getValidTo()));

        if (data.getAnimationIn() != null) {
            asset.setAnimationIn(data.getAnimationIn());
        }

        if (data.getAnimationOut() != null) {
            asset.setAnimationOut(data.getAnimationOut());
        }

        return assetRepository.save(asset);
    }

    public Boolean deleteAsset(String id) {
        final Asset asset = assetRepository.findById(id).orElseThrow(AssetNotFoundException::new);
        asset.setDeleted(true);
        asset.setUpdateDate(Instant.now());

        assetListRepository
                .findAllByAsset(id)
                .forEach(
                        assetList -> {
                            assetList
                                    .getAssets()
                                    .removeIf(assetEntry -> assetEntry.getAsset().equals(id));
                            assetList.setUpdateDate(Instant.now());
                            assetListRepository.save(assetList);
                        });

        assetRepository.save(asset);

        return true;
    }

    private void processAssetFile(Asset asset, FileUpload file) throws IOException {
        String type = getType(file.getContentType());
        String fileName = RandomStringUtils.random(20, true, true) + "." + type;

        // TODO předělat pak na MediaType
        switch (file.getContentType()) {
            case "image/png":
                asset.setType(Asset.Type.IMAGE_PNG);
                break;
            case "image/jpeg":
                asset.setType(Asset.Type.IMAGE_JPG);
                break;
            case "image/gif":
                asset.setType(Asset.Type.IMAGE_GIF);
                break;
            case "video/mp4":
                asset.setType(Asset.Type.VIDEO_MP4);
                break;
            case "video/quicktime":
                asset.setType(Asset.Type.VIDEO_MOV);
                break;
            case "video/x-msvideo":
                asset.setType(Asset.Type.VIDEO_AVI);
                break;
            case "video/x-ms-wmv":
                asset.setType(Asset.Type.VIDEO_WMV);
                break;
            default:
                throw new UnsupportedFileType();
        }

        try (FileOutputStream out = new FileOutputStream(getLocation(fileName))) {
            IOUtils.copy(file.getContent(), out);
        }

        asset.setPath(getPublicURL(fileName));
    }

    private File getLocation(String filename) throws IOException {
        File directory = resourceLoader.getResource("file:./data/").getFile();
        return new File(directory, filename);
    }

    public String getPublicURL(String fileLocation) {
        return MessageFormat.format(
                "{0}/storage/{1}", environment.getRequiredProperty("api.location"), fileLocation);
    }

    private String getType(String mimetype) {
        MediaType mediaType = MediaType.parseMediaType(mimetype);
        return mediaType.getSubtype();
    }
}
