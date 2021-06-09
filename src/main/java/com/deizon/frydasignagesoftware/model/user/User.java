package com.deizon.frydasignagesoftware.model.user;

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
@Document(collection = "users")
public class User extends Entity {

    private String username;
    private String password;
    private Role role; // TODO -> @See{Asset.class:28}

    public static Example<User> createExample(FindUserInput data) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        final User user = new User();

        if (data.getId() != null) {
            user.setId(data.getId());
            matcher = matcher.withMatcher("id", exact());
        }

        if (data.getUsername() != null) {
            user.setUsername(data.getUsername());
            matcher = matcher.withMatcher("username", exact());
        }

        if (data.getPassword() != null) {
            user.setPassword(data.getPassword());
            matcher = matcher.withMatcher("password", exact());
        }

        if (data.getRole() != null) {
            user.setRole(data.getRole());
            matcher = matcher.withMatcher("role", exact());
        }

        user.setDeleted(false);
        matcher.withMatcher("deleted", exact());

        return Example.of(user, matcher);
    }

    public enum Role {
        SUPER_USER("SUPER_USER"),
        ADMIN("ADMIN"),
        USER("USER");

        private final String name;

        Role(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
