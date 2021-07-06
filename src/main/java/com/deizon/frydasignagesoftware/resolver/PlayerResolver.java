/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.resolver;

import com.deizon.frydasignagesoftware.model.player.CreatePlayerInput;
import com.deizon.frydasignagesoftware.model.player.FindPlayerInput;
import com.deizon.frydasignagesoftware.model.player.Player;
import com.deizon.frydasignagesoftware.model.player.UpdatePlayerInput;
import com.deizon.frydasignagesoftware.repository.PlayerRepository;
import com.deizon.frydasignagesoftware.service.PlayerService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@PreAuthorize("isAuthenticated()")
public class PlayerResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final PlayerRepository playerRepository;
    private final PlayerService service;

    public Iterable<Player> findAllPlayers(FindPlayerInput input) {
        return this.service.findAll(input);
    }

    public Player findPlayer(FindPlayerInput input) {
        return this.service.find(input);
    }

    public Player createPlayer(CreatePlayerInput data) {
        return this.service.create(data);
    }

    public Player updatePlayer(String id, UpdatePlayerInput data) {
        return this.service.update(id, data);
    }

    public Player deletePlayer(String id) {
        return this.service.delete(id);
    }

    public boolean totalDeletePlayer(String id) {
        return this.service.totalDelete(id);
    }
}
