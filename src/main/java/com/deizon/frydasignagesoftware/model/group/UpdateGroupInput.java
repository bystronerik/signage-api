/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.model.group;

import com.deizon.services.model.ListChange;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateGroupInput {

    private String name;
    private String alert;
    private ListChange assetLists;
}
