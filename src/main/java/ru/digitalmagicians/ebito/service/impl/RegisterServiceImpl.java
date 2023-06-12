package ru.digitalmagicians.ebito.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalmagicians.ebito.dto.RegisterReq;
import ru.digitalmagicians.ebito.dto.Role;
import ru.digitalmagicians.ebito.exception.UserAlreadyExistException;
import ru.digitalmagicians.ebito.security.EbitoUserDetailsService;
import ru.digitalmagicians.ebito.service.RegisterService;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RegisterServiceImpl implements RegisterService {
    private final EbitoUserDetailsService service;

    @Override
    public boolean register(RegisterReq registerReq, Role role) {
        if (registerReq.getUsername().isBlank() || registerReq.getFirstName().isBlank()
                || registerReq.getLastName().isBlank() || registerReq.getPhone().isBlank()
                || registerReq.getPassword().isBlank()) {
            throw new IllegalArgumentException("All fields must be filled");
        }
        try {
            service.createUser(registerReq);
        } catch (UserAlreadyExistException e) {
            log.error(e.getMessage());
        }
        log.info("User {} successfully created", registerReq.getUsername());
        return true;
    }
}
