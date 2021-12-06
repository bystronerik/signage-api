package com.deizon.frydasignagesoftware.model.deploydata;

import com.deizon.frydasignagesoftware.model.alert.Alert;
import com.deizon.services.model.Validity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AlertData {

    private String id;
    private Alert.Position position;
    private String value;
    private Validity validity;

    private String background;
    private String borders;
    private String height;
    private String textSize;
    private String textColor;
    private String textPosition;

    private Boolean running;
}
