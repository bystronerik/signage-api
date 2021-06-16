/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.model.asset;

import com.deizon.frydasignagesoftware.model.FindInput;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FindAssetInput extends FindInput {

    private String name;
    private String path;
    private Asset.Type type;
    private String directory;
    private String tag;
}
