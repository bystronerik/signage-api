package com.deizon.frydasignagesoftware.resolver.entity;

import com.deizon.frydasignagesoftware.model.alert.Alert;
import com.deizon.frydasignagesoftware.model.style.Style;
import com.deizon.frydasignagesoftware.repository.StyleRepository;
import com.deizon.services.exception.ItemNotFoundException;
import com.deizon.services.resolver.BaseEntityResolver;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AlertEntityResolver extends BaseEntityResolver<Alert> {

    private final StyleRepository styleRepository;

    public CompletableFuture<Style> getBackground(Alert alert) {
        return this.processAsync(
                () -> {
                    if (alert.getBackground() == null) return null;

                    return styleRepository
                            .findById(alert.getBackground())
                            .orElseThrow(() -> new ItemNotFoundException(Style.class));
                });
    }

    public CompletableFuture<Style> getBorders(Alert alert) {
        return this.processAsync(
                () -> {
                    if (alert.getBorders() == null) return null;

                    return styleRepository
                            .findById(alert.getBorders())
                            .orElseThrow(() -> new ItemNotFoundException(Style.class));
                });
    }

    public CompletableFuture<Style> getHeight(Alert alert) {
        return this.processAsync(
                () -> {
                    if (alert.getHeight() == null) return null;

                    return styleRepository
                            .findById(alert.getHeight())
                            .orElseThrow(() -> new ItemNotFoundException(Style.class));
                });
    }

    public CompletableFuture<Style> getTextSize(Alert alert) {
        return this.processAsync(
                () -> {
                    if (alert.getTextSize() == null) return null;

                    return styleRepository
                            .findById(alert.getTextSize())
                            .orElseThrow(() -> new ItemNotFoundException(Style.class));
                });
    }

    public CompletableFuture<Style> getTextColor(Alert alert) {
        return this.processAsync(
                () -> {
                    if (alert.getTextColor() == null) return null;

                    return styleRepository
                            .findById(alert.getTextColor())
                            .orElseThrow(() -> new ItemNotFoundException(Style.class));
                });
    }

    public CompletableFuture<Style> getTextPosition(Alert alert) {
        return this.processAsync(
                () -> {
                    if (alert.getTextPosition() == null) return null;

                    return styleRepository
                            .findById(alert.getTextPosition())
                            .orElseThrow(() -> new ItemNotFoundException(Style.class));
                });
    }
}
