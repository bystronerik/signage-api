package com.deizon.frydasignagesoftware.model.alert;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

import com.deizon.frydasignagesoftware.model.Entity;
import com.deizon.frydasignagesoftware.model.Validity;
import lombok.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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

    public static Example<Alert> createExample(FindAlertInput data) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        final Alert alert = new Alert();

        if (data.getId() != null) {
            alert.setId(data.getId());
            matcher = matcher.withMatcher("id", exact());
        }

        if (data.getName() != null) {
            alert.setName(data.getName());
            matcher = matcher.withMatcher("name", exact());
        }

        if (data.getType() != null) {
            alert.setType(data.getType());
            matcher = matcher.withMatcher("type", exact());
        }

        if (data.getPosition() != null) {
            alert.setPosition(data.getPosition());
            matcher = matcher.withMatcher("position", exact());
        }

        if (data.getValue() != null) {
            alert.setValue(data.getValue());
            matcher = matcher.withMatcher("value", exact());
        }

        alert.setDeleted(false);
        matcher.withMatcher("deleted", exact());

        return Example.of(alert, matcher);
    }

    public enum Type {
        STANDALONE,
        BOUND,
    }

    public enum Position {
        TOP,
        MIDDLE,
        BOTTOM,
    }
}
