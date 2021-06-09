package com.deizon.frydasignagesoftware.model.assetlist;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAssetListInput {

    private String name;
    private String type;
    private String animationIn;
    private String animationOut;
    private Boolean validityEnabled;
    private Instant validFrom;
    private Instant validTo;
    private Boolean enabled;
}
