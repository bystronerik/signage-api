package com.deizon.frydasignagesoftware.model.playerinstance;

import com.deizon.services.model.Entity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "players.instances")
public class PlayerInstance extends Entity {

    private String player;
    private Boolean virtual;
    // TODO potřeba přidat další fieldy pro identifikaci a určení vlastností
    // TODO potřeba dodělat inputy podle používání ??

}
