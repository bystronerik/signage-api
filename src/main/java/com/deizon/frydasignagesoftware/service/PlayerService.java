/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.service;

import com.deizon.frydasignagesoftware.model.player.CreatePlayerInput;
import com.deizon.frydasignagesoftware.model.player.FindPlayerInput;
import com.deizon.frydasignagesoftware.model.player.Player;
import com.deizon.frydasignagesoftware.model.player.UpdatePlayerInput;
import com.deizon.frydasignagesoftware.repository.PlayerRepository;
import com.deizon.services.service.BaseService;
import com.deizon.services.util.EntityBuilder;
import com.deizon.services.util.ExampleBuilder;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class PlayerService
        extends BaseService<
                Player, CreatePlayerInput, UpdatePlayerInput, FindPlayerInput, PlayerRepository> {

    public PlayerService(PlayerRepository repository) {
        super(Player.class, repository, CreatePlayerInput.class, UpdatePlayerInput.class);
    }

    protected Example<Player> createExample(FindPlayerInput input) {
        final Player data = new Player();
        return new ExampleBuilder<>(data)
                .exact()
                .field("id", input::getId, data::setId)
                .field("name", input::getName, data::setName)
                .field("token", input::getToken, data::setToken)
                .field("group", input::getGroup, data::setGroup)
                .field("deleted", () -> false, data::setDeleted)
                .create();
    }

    @Override
    protected Player processData(Player entity, UpdatePlayerInput data) {
        return super.processData(
                new EntityBuilder<>(entity)
                        .field(data::getName, entity::setName)
                        .field(data::getToken, entity::setToken)
                        .field(data::getGroup, entity::setGroup)
                        .getEntity(),
                data);
    }
}
