package com.deizon.frydasignagesoftware.model.group;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateGroupInput {

    private String name;
    private String alert;
    private List<String> assetLists;
}
