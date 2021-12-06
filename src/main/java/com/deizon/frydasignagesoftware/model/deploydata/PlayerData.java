package com.deizon.frydasignagesoftware.model.deploydata;

import com.deizon.services.model.Entity;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "players.data")
public class PlayerData extends Entity {

    private String group;
    private String versionHash;

    private List<AssetData> assets;
    private List<AssetData> priorityAssets;
    private AlertData alert;
    private String styles;
}
