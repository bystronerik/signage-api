package com.deizon.frydasignagesoftware.model.alert;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAlertInput {

    private String name;
    private Alert.Type type;
    private Alert.Position position;
    private String value;

    private Boolean validityEnabled;
    private Instant validFrom;
    private Instant validTo;

    private String background;
    private String borders;
    private String height;
    private String textSize;
    private String textColor;
    private String textPosition;
}
