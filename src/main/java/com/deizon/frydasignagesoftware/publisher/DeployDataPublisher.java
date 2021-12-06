package com.deizon.frydasignagesoftware.publisher;

import com.deizon.frydasignagesoftware.model.deploydata.DeployData;
import com.deizon.frydasignagesoftware.repository.DeployDataRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.*;

@Component
public class DeployDataPublisher {

    private final Sinks.Many<DeployData> sink;

    public DeployDataPublisher(DeployDataRepository repository) {
        this.sink =
                repository.findAll().stream()
                        .findFirst()
                        .map(deployData -> Sinks.many().replay().latestOrDefault(deployData))
                        .orElseGet(() -> Sinks.many().replay().latest());
    }

    public Sinks.EmitResult publish(DeployData data) {
        return sink.tryEmitNext(data);
    }

    public Flux<DeployData> getPublisher() {
        return sink.asFlux().share();
    }
}
