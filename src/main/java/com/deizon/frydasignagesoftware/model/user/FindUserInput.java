package com.deizon.frydasignagesoftware.model.user;

import com.deizon.services.model.FindInput;
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
