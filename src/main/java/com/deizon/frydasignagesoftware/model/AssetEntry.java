package com.deizon.frydasignagesoftware.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetEntry {

    private String asset;
    private Validity validity;
}
