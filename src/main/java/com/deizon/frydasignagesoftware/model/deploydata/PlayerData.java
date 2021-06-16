/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.model.deploydata;

import com.deizon.frydasignagesoftware.model.AssetEntry;
import com.deizon.frydasignagesoftware.model.alert.Alert;
import com.deizon.frydasignagesoftware.model.style.Style;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerData {

    private List<AssetEntry> assets;
    private List<AssetEntry> priorityAssets;
    private Alert alert;
    private List<Style> styles;
}
