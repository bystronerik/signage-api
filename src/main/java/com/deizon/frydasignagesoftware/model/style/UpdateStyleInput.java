package com.deizon.frydasignagesoftware.model.style;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateStyleInput {

    private String name;
    private String value;
    private Style.Type type;
    private Style.ValueType valueType;
}
