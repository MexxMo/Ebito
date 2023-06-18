package ru.digitalmagicians.ebito.dto;

import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String image;

}
