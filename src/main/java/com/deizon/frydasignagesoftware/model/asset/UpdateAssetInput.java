/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.model.asset;

import com.deizon.services.model.ListChange;
import com.deizon.services.model.ValidityInput;
import java.time.Instant;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateAssetInput implements ValidityInput {

    private String name;
    private Integer showTime;
    private String animationIn;
    private String animationOut;
    private Boolean validityEnabled;
    private Instant validFrom;
    private Instant validTo;
    private String directory;
    private String alert;
    private ListChange tags;
}
