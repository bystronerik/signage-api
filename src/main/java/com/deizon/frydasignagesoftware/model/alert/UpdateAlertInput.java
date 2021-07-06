/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.model.alert;

import com.deizon.services.model.ValidityInput;
import java.time.Instant;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateAlertInput implements ValidityInput {

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

    private Boolean running;
}
