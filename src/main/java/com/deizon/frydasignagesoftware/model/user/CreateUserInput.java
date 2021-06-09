package com.deizon.frydasignagesoftware.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserInput {

    private String username;
    private String password;
    private User.Role role;
}
