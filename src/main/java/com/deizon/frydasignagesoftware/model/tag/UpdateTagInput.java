package com.deizon.frydasignagesoftware.model.tag;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateTagInput {

    private String name;
    private Tag.Color color;
}
