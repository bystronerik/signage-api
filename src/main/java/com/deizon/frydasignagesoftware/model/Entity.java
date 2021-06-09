package com.deizon.frydasignagesoftware.model;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Entity {

    @Id private String id;

    private Boolean deleted;
    private Instant createDate;
    private Instant updateDate;
}
