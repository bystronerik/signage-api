/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.resolver.entity;

import com.deizon.frydasignagesoftware.exception.ItemNotFoundException;
import com.deizon.frydasignagesoftware.model.alert.Alert;
import com.deizon.frydasignagesoftware.model.style.Style;
import com.deizon.frydasignagesoftware.repository.StyleRepository;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AlertEntityResolver implements GraphQLResolver<Alert> {

    private final StyleRepository styleRepository;

    public Style getBackground(Alert alert) {
        if (alert.getBackground() == null) return null;

        return styleRepository
                .findById(alert.getBackground())
                .orElseThrow(() -> new ItemNotFoundException(Style.class));
    }

    public Style getBorders(Alert alert) {
        if (alert.getBorders() == null) return null;

        return styleRepository
                .findById(alert.getBorders())
                .orElseThrow(() -> new ItemNotFoundException(Style.class));
    }

    public Style getHeight(Alert alert) {
        if (alert.getHeight() == null) return null;

        return styleRepository
                .findById(alert.getHeight())
                .orElseThrow(() -> new ItemNotFoundException(Style.class));
    }

    public Style getTextSize(Alert alert) {
        if (alert.getTextSize() == null) return null;

        return styleRepository
                .findById(alert.getTextSize())
                .orElseThrow(() -> new ItemNotFoundException(Style.class));
    }

    public Style getTextColor(Alert alert) {
        if (alert.getTextColor() == null) return null;

        return styleRepository
                .findById(alert.getTextColor())
                .orElseThrow(() -> new ItemNotFoundException(Style.class));
    }

    public Style getTextPosition(Alert alert) {
        if (alert.getTextPosition() == null) return null;

        return styleRepository
                .findById(alert.getTextPosition())
                .orElseThrow(() -> new ItemNotFoundException(Style.class));
    }
}
