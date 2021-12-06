package com.deizon.frydasignagesoftware.model.directory;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateDirectoryInput {

    private String name;
    private String parentDirectory;
}
