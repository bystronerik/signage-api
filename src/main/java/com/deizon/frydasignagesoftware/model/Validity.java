package com.deizon.frydasignagesoftware.model;

import java.time.Instant;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Validity {

    private Boolean enabled;
    private Instant from;
    private Instant to;

    public Boolean isValid() {
        final Instant now = Instant.now();
        return now.isAfter(from) && now.isBefore(to);
    }

    public Boolean isInFuture() {
        return Instant.now().isBefore(from);
    }

    public Boolean isInPast() {
        return Instant.now().isAfter(to);
    }
}
