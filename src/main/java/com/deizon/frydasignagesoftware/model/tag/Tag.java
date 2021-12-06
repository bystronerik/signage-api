package com.deizon.frydasignagesoftware.model.tag;

import com.deizon.services.model.Entity;
import lombok.*;
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

    public enum Color {}
}
