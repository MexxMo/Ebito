package ru.digitalmagicians.ebito.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.digitalmagicians.ebito.dto.NewPasswordDto;
import ru.digitalmagicians.ebito.dto.Role;
import ru.digitalmagicians.ebito.dto.UserDto;
import ru.digitalmagicians.ebito.entity.Image;
import ru.digitalmagicians.ebito.entity.User;
import ru.digitalmagicians.ebito.exception.UserNotFoundException;
import ru.digitalmagicians.ebito.mapper.UserMapper;
import ru.digitalmagicians.ebito.repository.UserRepository;
import ru.digitalmagicians.ebito.service.ImageService;
import ru.digitalmagicians.ebito.service.UserService;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;
    private final ImageService imageService;

    @Override
    public void setPassword(NewPasswordDto newPassword, Authentication authentication) {
        User user = getUserByEmail(authentication.getName());
        if (!encoder.matches(newPassword.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Passwords mismatch");
        }
        user.setPassword(encoder.encode(newPassword.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDto getUser(Authentication authentication) {
        User user = getUserByEmail(authentication.getName());
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Authentication authentication) {
        if (!validateUserDto(userDto)) {
            throw new IllegalArgumentException("All fields must be filled");
        }
        User user = getUserByEmail(authentication.getName());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateRole(Integer id, Role role) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        user.setRole(role);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public void updateAvatar(MultipartFile image, Authentication authentication) {
        User user = getUserByEmail(authentication.getName());
        Image oldImage = user.getImage();
        if (oldImage == null) {
            Image newImage = imageService.saveImage(image);
            user.setImage(newImage);
        } else {
            Image updatedImage = imageService.updateImage(image, oldImage);
            user.setImage(updatedImage);
        }
        userRepository.save(user);
    }


    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmailIgnoreCase(email).orElseThrow(UserNotFoundException::new);
    }

    /**
     * Проверяет, заполнены ли все поля в объекте UserDto.
     *
     * @param dto объект UserDto для проверки
     * @return true, если все поля заполнены, иначе - false
     */
    private boolean validateUserDto(UserDto dto) {
        return !dto.getFirstName().isBlank() || !dto.getLastName().isBlank() || !dto.getPhone().isBlank();
    }
}
