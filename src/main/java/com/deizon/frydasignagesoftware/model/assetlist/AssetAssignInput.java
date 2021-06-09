package com.deizon.frydasignagesoftware.model.assetlist;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetAssignInput {

    private String asset;
    private Boolean validityEnabled;
    private Instant validFrom;
    private Instant validTo;
}
