package com.deizon.frydasignagesoftware.model.asset;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAssetInput {

    private String name;
    private Integer showTime;
    private String animationIn;
    private String animationOut;
    private Boolean validityEnabled;
    private Instant validFrom;
    private Instant validTo;
}
