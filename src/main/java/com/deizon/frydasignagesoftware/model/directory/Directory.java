/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.model.directory;

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
@Document(collection = "assets.directories")
public class Directory extends Entity {

    private String name;
    private String parentDirectory;

    public static Example<Directory> createExample(FindDirectoryInput data) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        final Directory directory = new Directory();

        if (data.getId() != null) {
            directory.setId(data.getId());
            matcher = matcher.withMatcher("id", exact());
        }

        if (data.getName() != null) {
            directory.setName(data.getName());
            matcher = matcher.withMatcher("name", exact());
        }

        if (data.getParentDirectory() != null) {
            directory.setParentDirectory(data.getParentDirectory());
            matcher = matcher.withMatcher("parentDirectory", exact());
        }

        directory.setDeleted(false);
        matcher.withMatcher("deleted", exact());

        return Example.of(directory, matcher);
    }
}
