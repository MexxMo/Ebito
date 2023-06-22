package ru.digitalmagicians.ebito.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.digitalmagicians.ebito.dto.NewPasswordDto;
import ru.digitalmagicians.ebito.dto.Role;
import ru.digitalmagicians.ebito.dto.UserDto;
import ru.digitalmagicians.ebito.service.ImageService;
import ru.digitalmagicians.ebito.service.UserService;


@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("users")
@Tag(name = "Пользователи")
public class UserController {

    private final UserService service;
    private final ImageService imageService;


    @Operation(summary = "Обновление пароля",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            })
    @PostMapping("set_password")
    public ResponseEntity<NewPasswordDto> setPassword(@RequestBody NewPasswordDto newPassword,
                                                      Authentication authentication) {
        service.setPassword(newPassword, authentication);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "Получить информацию об авторизованном пользователе",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class)
                            )}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            })
    @GetMapping("me")
    public ResponseEntity<UserDto> getUser(Authentication authentication) {
        return service.getUser(authentication) != null
                ? ResponseEntity.ok(service.getUser(authentication))
                : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Operation(summary = "Обновить информацию об авторизованном пользователе",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            })
    @PatchMapping("me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, Authentication authentication) {
        UserDto user = service.updateUser(userDto, authentication);
        return user != null
                ? ResponseEntity.ok(user)
                : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Operation(summary = "Обновить роль пользователя (для администратора)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = " Forbidden"),
            }
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("role")
    public ResponseEntity<UserDto> updateUserRole(@RequestParam Integer userId,
                                                  @RequestParam Role role) {
        UserDto user = service.updateRole(userId, role);
        return user != null
                ? ResponseEntity.ok(user)
                : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    @Operation(summary = "Обновить аватар авторизованного пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            })
    @PatchMapping(value = "me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateUserImage(@RequestPart("image") MultipartFile image,
                                                  Authentication authentication) {
        service.updateAvatar(image, authentication);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "Получить аватар пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content())
            })
    @GetMapping(value = "/image/{id}", produces = {
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.APPLICATION_OCTET_STREAM_VALUE,
            MediaType.IMAGE_GIF_VALUE
    })
    public ResponseEntity<byte[]> getImage(@PathVariable("id") String id) {
        return imageService.loadImage(id) != null
                ? ResponseEntity.ok(imageService.loadImage(id))
                : ResponseEntity.notFound().build();
    }
}
