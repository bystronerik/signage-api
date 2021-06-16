/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.model.auth;

import com.deizon.frydasignagesoftware.model.user.User;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDetails {

    private User user;
    private Token token;
}
