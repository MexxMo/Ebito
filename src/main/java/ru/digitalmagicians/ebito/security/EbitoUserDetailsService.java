package ru.digitalmagicians.ebito.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.digitalmagicians.ebito.dto.RegisterReq;
import ru.digitalmagicians.ebito.dto.Role;
import ru.digitalmagicians.ebito.entity.User;
import ru.digitalmagicians.ebito.exception.UserAlreadyExistException;
import ru.digitalmagicians.ebito.exception.UserNotFoundException;
import ru.digitalmagicians.ebito.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class EbitoUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmailIgnoreCase(username).orElseThrow(UserNotFoundException::new);
        return new EbitoUserDetails(user);
    }

    public void createUser(RegisterReq registerReq) {
        if (userRepository.existsByEmailIgnoreCase(registerReq.getUsername())) {
            throw new UserAlreadyExistException();
        }
        User user = new User();
        user.setFirstName(registerReq.getFirstName());
        user.setLastName(registerReq.getLastName());
        user.setEmail(registerReq.getUsername());
        user.setPassword(encoder.encode(registerReq.getPassword()));
        user.setPhone(registerReq.getPhone());
        user.setRole(Role.USER);

        userRepository.save(user);
        log.debug("User successfully created = {}", user);
    }
}
