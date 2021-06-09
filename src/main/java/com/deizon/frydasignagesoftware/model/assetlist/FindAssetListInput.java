package com.deizon.frydasignagesoftware.model.assetlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindAssetListInput {

    private String id;
    private String name;
    private String type;
    private Boolean enabled;
}
