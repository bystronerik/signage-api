/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.model.user;

import com.deizon.services.model.Entity;
import lombok.*;
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
    private Role role;

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
