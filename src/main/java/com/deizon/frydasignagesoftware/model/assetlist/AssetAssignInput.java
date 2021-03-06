package com.deizon.frydasignagesoftware.model.assetlist;

import com.deizon.services.model.ValidityInput;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetAssignInput implements ValidityInput {

    private String asset;
    private Boolean validityEnabled;
    private Instant validFrom;
    private Instant validTo;
    private Integer showTime;
    private String animationIn;
    private String animationOut;
    private Integer position;
}
