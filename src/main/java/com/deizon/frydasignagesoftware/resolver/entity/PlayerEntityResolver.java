/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.resolver.entity;

import com.deizon.frydasignagesoftware.model.group.Group;
import com.deizon.frydasignagesoftware.model.player.Player;
import com.deizon.frydasignagesoftware.repository.GroupRepository;
import com.deizon.services.exception.ItemNotFoundException;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PlayerEntityResolver implements GraphQLResolver<Player> {

    private final GroupRepository groupRepository;

    public Group getGroup(Player player) {
        if (player.getGroup() == null) return null;
        return groupRepository
                .findById(player.getGroup())
                .orElseThrow(() -> new ItemNotFoundException(Group.class));
    }
}
