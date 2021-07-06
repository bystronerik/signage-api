/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.model.alert;

import com.deizon.services.model.Entity;
import com.deizon.services.model.Validity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "alerts")
public class Alert extends Entity {

    private String name;
    private Type type;
    private Position position;
    private String value;
    private Validity validity;

    private String background;
    private String borders;
    private String height;
    private String textSize;
    private String textColor;
    private String textPosition;

    private Boolean running;

    public enum Type {
        STANDALONE,
        BOUND,
    }

    public enum Position {
        TOP,
        MIDDLE,
        BOTTOM,
        LEFT_TOP_RIGHT_BOTTOM,
        LEFT_BOTTOM_RIGHT_TOP
    }
}
