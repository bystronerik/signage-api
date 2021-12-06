package com.deizon.frydasignagesoftware.model.player;

import com.deizon.services.model.Entity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "players")
public class Player extends Entity {

    private String name;
    private String token;
    private String group;
}
