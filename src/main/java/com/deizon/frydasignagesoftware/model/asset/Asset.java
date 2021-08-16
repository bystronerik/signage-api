/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.model.asset;

import com.deizon.services.model.Entity;
import com.deizon.services.model.Validity;
import com.deizon.services.model.file.FileType;
import java.util.ArrayList;
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
    private FileType type;

    private String alert;
    private Validity validity;

    private String animationIn;
    private String animationOut;

    private String directory;
    private List<String> tags;

    public List<String> getTags() {
        if (this.tags == null) this.tags = new ArrayList<>();

        return this.tags;
    }
}
