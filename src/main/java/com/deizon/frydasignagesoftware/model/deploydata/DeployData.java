/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.model.deploydata;

import com.deizon.frydasignagesoftware.model.Entity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "deployData")
public class DeployData extends Entity {

    private String versionHash;
}
