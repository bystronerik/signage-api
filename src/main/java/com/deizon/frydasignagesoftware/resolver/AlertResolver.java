package com.deizon.frydasignagesoftware.resolver;

import com.deizon.frydasignagesoftware.exception.StyleNotFoundException;
import com.deizon.frydasignagesoftware.model.alert.Alert;
import com.deizon.frydasignagesoftware.model.style.Style;
import com.deizon.frydasignagesoftware.repository.StyleRepository;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AlertResolver implements GraphQLResolver<Alert> {

    private final StyleRepository styleRepository;

    public Style getBackground(Alert alert) {
        if (alert.getBackground() == null) return null;

        return styleRepository
                .findById(alert.getBackground())
                .orElseThrow(StyleNotFoundException::new);
    }

    public Style getBorders(Alert alert) {
        if (alert.getBorders() == null) return null;

        return styleRepository
                .findById(alert.getBorders())
                .orElseThrow(StyleNotFoundException::new);
    }

    public Style getHeight(Alert alert) {
        if (alert.getHeight() == null) return null;

        return styleRepository.findById(alert.getHeight()).orElseThrow(StyleNotFoundException::new);
    }

    public Style getTextSize(Alert alert) {
        if (alert.getTextSize() == null) return null;

        return styleRepository
                .findById(alert.getTextSize())
                .orElseThrow(StyleNotFoundException::new);
    }

    public Style getTextColor(Alert alert) {
        if (alert.getTextColor() == null) return null;

        return styleRepository
                .findById(alert.getTextColor())
                .orElseThrow(StyleNotFoundException::new);
    }

    public Style getTextPosition(Alert alert) {
        if (alert.getTextPosition() == null) return null;

        return styleRepository
                .findById(alert.getTextPosition())
                .orElseThrow(StyleNotFoundException::new);
    }
}
