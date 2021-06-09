package com.deizon.frydasignagesoftware.model.player;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

import com.deizon.frydasignagesoftware.model.Entity;
import lombok.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "players")
public class Player extends Entity {

    private String name;
    private String token;
    private String group;

    public static Example<Player> createExample(FindPlayerInput data) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        final Player player = new Player();

        if (data.getId() != null) {
            player.setId(data.getId());
            matcher = matcher.withMatcher("id", exact());
        }

        if (data.getName() != null) {
            player.setName(data.getName());
            matcher = matcher.withMatcher("name", exact());
        }

        if (data.getToken() != null) {
            player.setToken(data.getToken());
            matcher = matcher.withMatcher("token", exact());
        }

        player.setDeleted(false);
        matcher.withMatcher("deleted", exact());

        return Example.of(player, matcher);
    }
}
