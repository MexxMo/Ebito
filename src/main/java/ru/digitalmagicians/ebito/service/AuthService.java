package ru.digitalmagicians.ebito.service;

import ru.digitalmagicians.ebito.dto.RegisterReq;
import ru.digitalmagicians.ebito.dto.Role;

public interface AuthService {
    boolean login(String userName, String password);

    boolean register(RegisterReq registerReq, Role role);
}
