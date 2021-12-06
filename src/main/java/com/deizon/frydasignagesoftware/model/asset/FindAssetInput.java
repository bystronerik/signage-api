package com.deizon.frydasignagesoftware.model.asset;

import com.deizon.services.model.FindInput;
import com.deizon.services.model.file.FileType;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FindAssetInput extends FindInput {

    private String name;
    private String path;
    private FileType type;
    private String directory;
    private List<String> tags;
}
