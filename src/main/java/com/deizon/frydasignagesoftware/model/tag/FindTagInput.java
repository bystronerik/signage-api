package com.deizon.frydasignagesoftware.model.tag;

import com.deizon.services.model.FindInput;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FindTagInput extends FindInput {

    private String name;
    private Tag.Color color;
}
