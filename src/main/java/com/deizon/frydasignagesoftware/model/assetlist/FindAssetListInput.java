/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.model.assetlist;

import com.deizon.services.model.FindInput;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FindAssetListInput extends FindInput {

    private String name;
    private String type;
    private Boolean prioritized;
    private Boolean enabled;
}
