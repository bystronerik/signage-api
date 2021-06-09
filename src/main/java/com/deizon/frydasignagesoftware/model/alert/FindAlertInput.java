package com.deizon.frydasignagesoftware.model.alert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindAlertInput {

    private String id;
    private String name;
    private Alert.Type type;
    private Alert.Position position;
    private String value;
}
