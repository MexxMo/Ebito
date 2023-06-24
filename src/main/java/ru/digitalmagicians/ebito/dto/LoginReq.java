package ru.digitalmagicians.ebito.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginReq {
    private String password;
    private String username;

}
