package com.deizon.frydasignagesoftware.model.directory;

import com.deizon.services.model.Entity;
import lombok.*;
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
}
