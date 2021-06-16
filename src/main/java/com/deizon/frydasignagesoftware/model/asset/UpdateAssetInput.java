/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.model.asset;

import java.time.Instant;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateAssetInput {

    private String name;
    private Integer showTime;
    private String animationIn;
    private String animationOut;
    private Boolean validityEnabled;
    private Instant validFrom;
    private Instant validTo;
    private String directory;
    private List<String> tags;
}
