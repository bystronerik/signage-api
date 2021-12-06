package com.deizon.frydasignagesoftware.model.assetlist;

import com.deizon.services.model.ValidityInput;
import java.time.Instant;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateAssetListInput implements ValidityInput {

    private String name;
    private String type;
    private String animationIn;
    private String animationOut;
    private Boolean validityEnabled;
    private Instant validFrom;
    private Instant validTo;
    private Boolean prioritized;
    private Boolean enabled;
}
