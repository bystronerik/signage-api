package com.deizon.frydasignagesoftware.model.alert;

import com.deizon.services.model.FindInput;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FindAlertInput extends FindInput {

    private String name;
    private Alert.Type type;
    private Alert.Position position;
    private String value;
}
