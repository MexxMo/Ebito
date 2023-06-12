package ru.digitalmagicians.ebito.service;

import ru.digitalmagicians.ebito.dto.RegisterReq;
import ru.digitalmagicians.ebito.dto.Role;

public interface RegisterService {
        boolean register(RegisterReq registerReq, Role role);
    }
