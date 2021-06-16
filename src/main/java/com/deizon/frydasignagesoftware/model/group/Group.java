/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.model.group;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

import com.deizon.frydasignagesoftware.model.Entity;
import java.util.List;
import lombok.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "players.groups")
public class Group extends Entity {

    private String name;
    private String alert;
    private List<String> assetLists;

    public static Example<Group> createExample(FindGroupInput data) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        final Group group = new Group();

        if (data.getId() != null) {
            group.setId(data.getId());
            matcher = matcher.withMatcher("id", exact());
        }

        if (data.getName() != null) {
            group.setName(data.getName());
            matcher = matcher.withMatcher("name", exact());
        }

        group.setDeleted(false);
        matcher.withMatcher("deleted", exact());

        return Example.of(group, matcher);
    }
}
