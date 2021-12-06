package com.deizon.frydasignagesoftware.model.player;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdatePlayerInput {

    private String name;
    private String token;
    private String group;
}
