package com.deizon.frydasignagesoftware.resolver.entity;

import com.deizon.frydasignagesoftware.model.deploydata.AssetData;
import com.deizon.services.resolver.BaseEntityResolver;
import java.text.MessageFormat;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AssetDataResolver extends BaseEntityResolver<AssetData> {

    private final Environment environment;

    public CompletableFuture<String> getPath(AssetData data) {
        return this.processAsync(
                () ->
                        MessageFormat.format(
                                "{0}/{1}",
                                this.environment.getRequiredProperty("storage.location"),
                                data.getPath()));
    }
}
