package com.deizon.frydasignagesoftware.resolver;

import com.deizon.frydasignagesoftware.exception.GroupNotFoundException;
import com.deizon.frydasignagesoftware.model.group.Group;
import com.deizon.frydasignagesoftware.model.player.Player;
import com.deizon.frydasignagesoftware.repository.GroupRepository;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PlayerResolver implements GraphQLResolver<Player> {

    private final GroupRepository groupRepository;

    public Group getGroup(Player player) {
        if (player.getGroup() == null) return null;
        return groupRepository.findById(player.getGroup()).orElseThrow(GroupNotFoundException::new);
    }
}
