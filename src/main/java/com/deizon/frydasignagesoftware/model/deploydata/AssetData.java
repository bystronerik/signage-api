package com.deizon.frydasignagesoftware.model.deploydata;

import com.deizon.services.model.Validity;
import com.deizon.services.model.file.FileType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AssetData {

    private String id;
    private String path;
    private Integer showTime;
    private FileType type;

    private AlertData alert;
    private Validity validity;

    private String animationIn;
    private String animationOut;
}
