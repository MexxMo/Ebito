package ru.digitalmagicians.ebito.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalmagicians.ebito.exception.UserNotFoundException;
import ru.digitalmagicians.ebito.security.EbitoUserDetailsService;
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
}
