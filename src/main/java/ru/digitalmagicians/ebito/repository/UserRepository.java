package ru.digitalmagicians.ebito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.digitalmagicians.ebito.dto.Role;
import ru.digitalmagicians.ebito.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> getUserByEmailIgnoreCase(String email);

    Optional<User> findByRole(Role role);

    boolean existsByEmailIgnoreCase(String email);

}
