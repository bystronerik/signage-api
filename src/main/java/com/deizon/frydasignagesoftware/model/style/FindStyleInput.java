package com.deizon.frydasignagesoftware.model.style;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindStyleInput {

    private String id;
    private String name;
    private Style.Type type;
    private Style.ValueType valueType;
}
