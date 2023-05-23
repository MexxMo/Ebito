package ru.digitalmagicians.ebito.dto;

import lombok.Data;

@Data
public class NewPassword {
    private String currentPassword;
    private String newPassword;
}
