/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.model;

import com.deizon.services.model.Validity;
import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetEntry {

    @Id private String id;
    private String asset;
    private Validity validity;
    private Integer showTime;
    private String animationIn;
    private String animationOut;
    private Integer position;
}
