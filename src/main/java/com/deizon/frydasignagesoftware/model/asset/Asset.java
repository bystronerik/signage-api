/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.model.asset;

import com.deizon.services.model.Validity;
import com.deizon.services.model.Entity;
import java.util.List;
import lombok.*;
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

    private String directory;
    private List<String> tags;

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
