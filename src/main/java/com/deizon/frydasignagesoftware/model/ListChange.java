/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListChange {

    private MergeStrategy mergeStrategy;
    private List<String> content;

    public void process(List<String> data) {
        if (this.getMergeStrategy().equals(MergeStrategy.OVERRIDE)) {
            data.clear();
            data.addAll(this.getContent());
        }

        if (this.getMergeStrategy().equals(MergeStrategy.EXTEND)) {
            data.addAll(this.getContent());
        }

        if (this.getMergeStrategy().equals(MergeStrategy.REDUCE)) {
            data.removeAll(this.getContent());
        }
    }

    public enum MergeStrategy {
        OVERRIDE,
        EXTEND,
        REDUCE
    }
}
