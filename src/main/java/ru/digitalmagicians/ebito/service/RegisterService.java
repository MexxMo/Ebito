package ru.digitalmagicians.ebito.service;

import ru.digitalmagicians.ebito.dto.RegisterReq;
import ru.digitalmagicians.ebito.dto.Role;

public interface RegisterService {
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
