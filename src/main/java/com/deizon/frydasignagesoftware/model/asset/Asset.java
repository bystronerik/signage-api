package com.deizon.frydasignagesoftware.model.asset;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

import com.deizon.frydasignagesoftware.model.Entity;
import com.deizon.frydasignagesoftware.model.Validity;
import lombok.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "assets")
public class Asset extends Entity {

    private String name;
    private String path;
    private Integer showTime;
    private Type type;

    private String alert;
    private Validity validity;

    private String animationIn;
    private String animationOut;

    public static Example<Asset> createExample(FindAssetInput data) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        final Asset asset = new Asset();

        if (data.getId() != null) {
            asset.setId(data.getId());
            matcher = matcher.withMatcher("id", exact());
        }

        if (data.getName() != null) {
            asset.setName(data.getName());
            matcher = matcher.withMatcher("name", exact());
        }

        if (data.getPath() != null) {
            asset.setPath(data.getPath());
            matcher = matcher.withMatcher("path", exact());
        }

        if (data.getType() != null) {
            asset.setType(data.getType());
            matcher = matcher.withMatcher("type", exact());
        }

        asset.setDeleted(false);
        matcher.withMatcher("deleted", exact());

        return Example.of(asset, matcher);
    }

    public enum Type {
        IMAGE_PNG("image/png"),
        IMAGE_JPG("image/jpeg"),
        IMAGE_GIF("image/gif"),
        VIDEO_MP4("video/mp4"),
        VIDEO_AVI("video/x-msvideo"),
        VIDEO_MOV("video/quicktime"),
        VIDEO_WMV("video/x-ms-wmv");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
