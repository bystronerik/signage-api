/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.model.assetlist;

import com.deizon.frydasignagesoftware.model.AssetEntry;
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
@Document(collection = "assets.assetLists")
public class AssetList extends Entity {

    private String name;
    private String type;
    private Validity validity;
    private Boolean prioritized;
    private Boolean enabled;
    private List<AssetEntry> assets;

    private String animationIn;
    private String animationOut;
}
