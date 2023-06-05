package ru.digitalmagicians.ebito.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalmagicians.ebito.security.EbitoUserDetailsService;
import ru.digitalmagicians.ebito.dto.RegisterReq;
import ru.digitalmagicians.ebito.dto.Role;
import ru.digitalmagicians.ebito.exception.UserAlreadyExistException;
import ru.digitalmagicians.ebito.exception.UserNotFoundException;
import ru.digitalmagicians.ebito.service.AuthService;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final EbitoUserDetailsService manager;

    private final PasswordEncoder encoder;

    @Override
    public boolean login(String userName, String password) {
        try {
            UserDetails userDetails = manager.loadUserByUsername(userName);
            return encoder.matches(password, userDetails.getPassword());
        } catch (UserNotFoundException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean register(RegisterReq registerReq, Role role) {
        if (registerReq.getUsername().isBlank() || registerReq.getFirstName().isBlank()
                || registerReq.getLastName().isBlank() || registerReq.getPhone().isBlank()
                || registerReq.getPassword().isBlank()) {
            throw new IllegalArgumentException("All fields must be filled");
        }
        try {
            manager.createUser(registerReq);
        } catch (UserAlreadyExistException e) {
            log.error(e.getMessage());
        }
        log.info("User {} successfully created", registerReq.getUsername());
        return true;
    }
}
