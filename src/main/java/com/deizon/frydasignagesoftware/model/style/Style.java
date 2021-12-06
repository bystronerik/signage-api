package com.deizon.frydasignagesoftware.model.style;

import com.deizon.services.model.Entity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "styles")
public class Style extends Entity {

    private String name;
    private String value;
    private ValueType valueType;
    private Type type;

    public enum Type {
        ANIMATION,
        BORDER,
        BACKGROUND,
        TEXT_COLOR,
        TEXT_SIZE,
        TEXT_ALIGN,
        HEIGHT,
    }

    public enum ValueType {
        PURE_CSS,
        TAILWINDCSS,
        ANIMATECSS,
    }
}
