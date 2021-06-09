package com.deizon.frydasignagesoftware.model.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePlayerInput {

    private String name;
    private String token;
    private String group;
}
