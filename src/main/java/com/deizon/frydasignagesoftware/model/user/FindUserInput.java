/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.model.user;

import com.deizon.frydasignagesoftware.model.FindInput;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FindUserInput extends FindInput {

    private String username;
    private String password;
    private User.Role role;
}
