package com.deizon.frydasignagesoftware.model.style;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStyleInput {

    private String name;
    private String value;
    private Style.Type type;
    private Style.ValueType valueType;
}
