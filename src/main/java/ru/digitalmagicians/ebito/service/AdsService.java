package ru.digitalmagicians.ebito.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.digitalmagicians.ebito.dto.AdsDto;
import ru.digitalmagicians.ebito.dto.CreateAdsDto;
import ru.digitalmagicians.ebito.dto.FullAdsDto;
import ru.digitalmagicians.ebito.dto.ResponseWrapperAdsDto;
import ru.digitalmagicians.ebito.entity.Ads;


/**
 * Сервис для работы с объявлениями.
 */
public interface AdsService {
    /**
     * @param image          файл изображения для объявления
     * @param properties     объект CreateAdsDto, содержащий свойства объявления
     * @param authentication объект аутентификации пользователя, создающего объявление
     * @return AdsDto объект, представляющий созданное объявление
     */
    AdsDto createAds(MultipartFile image, CreateAdsDto properties, Authentication authentication);

    /**
     * Обновляет объявление с указанным идентификатором с помощью переданных свойств.
     *
     * @param id     идентификатор объявления для обновления
     * @param adsDto объект CreateAdsDto, содержащий новые свойства объявления
     * @return AdsDto объект, представляющий обновленное объявление
     */
    AdsDto updateAds(Integer id, CreateAdsDto adsDto);

    /**
     * Обновляет изображение объявления с указанным идентификатором.
     *
     * @param id    идентификатор объявления, для которого нужно обновить изображение
     * @param image новый файл изображения
     */
    void updateAdsImage(Integer id, MultipartFile image);

    /**
     * Возвращает список всех объявлений в виде объекта ResponseWrapperAdsDto.
     *
     * @return объект ResponseWrapperAdsDto, содержащий список всех объявлений
     */
    ResponseWrapperAdsDto getAll();

    /**
     * Возвращает список всех объявлений, созданных текущим пользователем, в виде объекта ResponseWrapperAdsDto.
     *
     * @param authentication объект аутентификации текущего пользователя
     * @return объект ResponseWrapperAdsDto, содержащий список всех объявлений, созданных текущим пользователем
     */
    ResponseWrapperAdsDto getAllByMe(Authentication authentication);

    /**
     * Возвращает объявление с указанным идентификатором в виде объекта FullAdsDto.
     *
     * @param id идентификатор объявления для поиска
     * @return объект FullAdsDto, представляющий найденное объявление
     */
    FullAdsDto getById(Integer id);

    /**
     * Удаляет объявление с указанным идентификатором.
     *
     * @param id идентификатор объявления для удаления
     */
    void delete(Integer id);

    /**
     * Возвращает объявление с указанным идентификатором в виде объекта Ads.
     *
     * @param id идентификатор объявления для поиска
     * @return объект Ads, представляющий найденное объявление
     */
    Ads getAdsById(Integer id);

    /**
     * Возвращает список всех объявлений, содержащих указанный текст в заголовке, в виде объекта ResponseWrapperAdsDto.
     *
     * @param search текст для поиска в заголовках объявлений
     * @return объект ResponseWrapperAdsDto, содержащий список найденных объявлений
     */
    ResponseWrapperAdsDto getAll(String search);
}
