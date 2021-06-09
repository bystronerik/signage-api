package com.deizon.frydasignagesoftware.model.assetlist;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

import com.deizon.frydasignagesoftware.model.AssetEntry;
import com.deizon.frydasignagesoftware.model.Entity;
import com.deizon.frydasignagesoftware.model.Validity;
import java.util.List;
import lombok.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "assets.assetLists")
public class AssetList extends Entity {

    private String name;
    private String type;
    private Validity validity;
    private Boolean enabled;
    private List<AssetEntry> assets;

    private String animationIn;
    private String animationOut;

    public static Example<AssetList> createExample(FindAssetListInput data) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        final AssetList assetList = new AssetList();

        if (data.getId() != null) {
            assetList.setId(data.getId());
            matcher = matcher.withMatcher("id", exact());
        }

        if (data.getName() != null) {
            assetList.setName(data.getName());
            matcher = matcher.withMatcher("name", exact());
        }

        if (data.getType() != null) {
            assetList.setType(data.getType());
            matcher = matcher.withMatcher("type", exact());
        }

        if (data.getEnabled() != null) {
            assetList.setEnabled(data.getEnabled());
            matcher = matcher.withMatcher("enabled", exact());
        }

        assetList.setDeleted(false);
        matcher.withMatcher("deleted", exact());

        return Example.of(assetList, matcher);
    }
}
