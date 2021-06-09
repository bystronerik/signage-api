package com.deizon.frydasignagesoftware.model.style;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

import com.deizon.frydasignagesoftware.model.Entity;
import lombok.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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

    public static Example<Style> createExample(FindStyleInput data) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        final Style style = new Style();

        if (data.getId() != null) {
            style.setId(data.getId());
            matcher = matcher.withMatcher("id", exact());
        }

        if (data.getName() != null) {
            style.setName(data.getName());
            matcher = matcher.withMatcher("name", exact());
        }

        if (data.getType() != null) {
            style.setType(data.getType());
            matcher = matcher.withMatcher("type", exact());
        }

        if (data.getValueType() != null) {
            style.setValueType(data.getValueType());
            matcher = matcher.withMatcher("valueType", exact());
        }

        style.setDeleted(false);
        matcher.withMatcher("deleted", exact());

        return Example.of(style, matcher);
    }

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
