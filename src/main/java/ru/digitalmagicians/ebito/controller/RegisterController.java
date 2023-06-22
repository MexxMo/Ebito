package ru.digitalmagicians.ebito.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.digitalmagicians.ebito.dto.RegisterReq;
import ru.digitalmagicians.ebito.service.RegisterService;

import static ru.digitalmagicians.ebito.dto.Role.USER;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/register")
@RestController
@RequiredArgsConstructor
@Tag(name = "Регистрация")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "ОК. Пользователь зарегистрирован",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = @ArraySchema(schema = @Schema(implementation = RegisterReq.class)))),
        @ApiResponse(responseCode = "400", description = "Ошибка 400 BAD Request. Параметры запроса некорректны")
})
public class RegisterController {
    private final RegisterService service;

    @PostMapping
    @Operation(summary = "Регистрация пользователя")
    public ResponseEntity<?> register(@RequestBody RegisterReq req) {
        if (req.getRole() == null) {
            req.setRole(USER);
        }
        return service.register(req)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
