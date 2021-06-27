/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.model.group;

import com.deizon.services.model.Entity;
import java.util.List;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "players.groups")
public class Group extends Entity {

    private String name;
    private String alert;
    private List<String> assetLists;
}
