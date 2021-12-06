package com.deizon.frydasignagesoftware.resolver.entity;

import com.deizon.frydasignagesoftware.model.group.Group;
import com.deizon.frydasignagesoftware.model.player.Player;
import com.deizon.frydasignagesoftware.repository.GroupRepository;
import com.deizon.services.exception.ItemNotFoundException;
import com.deizon.services.resolver.BaseEntityResolver;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PlayerEntityResolver extends BaseEntityResolver<Player> {

    private final GroupRepository groupRepository;

    public CompletableFuture<Group> getGroup(Player player) {
        return this.processAsync(
                () -> {
                    if (player.getGroup() == null) return null;
                    return groupRepository
                            .findById(player.getGroup())
                            .orElseThrow(() -> new ItemNotFoundException(Group.class));
                });
    }
}
