/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.model.tag;

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
@Document(collection = "assets.tags")
public class Tag extends Entity {

    private String name;
    private Color color;

    public static Example<Tag> createExample(FindTagInput data) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        final Tag tag = new Tag();

        if (data.getId() != null) {
            tag.setId(data.getId());
            matcher = matcher.withMatcher("id", exact());
        }

        if (data.getName() != null) {
            tag.setName(data.getName());
            matcher = matcher.withMatcher("name", exact());
        }

        if (data.getColor() != null) {
            tag.setColor(data.getColor());
            matcher = matcher.withMatcher("color", exact());
        }

        tag.setDeleted(false);
        matcher.withMatcher("deleted", exact());

        return Example.of(tag, matcher);
    }

    public enum Color {}
}
