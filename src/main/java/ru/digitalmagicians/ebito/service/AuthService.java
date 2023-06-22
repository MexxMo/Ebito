package ru.digitalmagicians.ebito.service;

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
}
