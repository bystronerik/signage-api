package com.deizon.frydasignagesoftware.model.asset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindAssetInput {

    private String id;
    private String name;
    private String path;
    private Asset.Type type;
}
