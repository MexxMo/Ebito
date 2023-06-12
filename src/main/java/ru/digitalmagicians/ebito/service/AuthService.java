package ru.digitalmagicians.ebito.service;

import ru.digitalmagicians.ebito.dto.RegisterReq;
import ru.digitalmagicians.ebito.dto.Role;

/**
 * Сервис для аутентификации и регистрации пользователей.
 */
public interface AuthService {
    /**
     * Метод для авторизации пользователя.
     *
     * @param userName имя пользователя.
     * @param password пароль пользователя.
     * @return true, если пользователь авторизован успешно, иначе - false.
     */
    boolean login(String userName, String password);

    /**
     * Метод для регистрации нового пользователя.
     *
     * @param registerReq данные для регистрации нового пользователя.
     * @param role        роль пользователя.
     * @return true, если пользователь успешно зарегистрирован, иначе - false.
     * @throws IllegalArgumentException если какое-либо поле не заполнено.
     */
    boolean register(RegisterReq registerReq, Role role);
}
