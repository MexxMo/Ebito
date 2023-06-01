package ru.digitalmagicians.ebito.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.digitalmagicians.ebito.dto.AdsDto;
import ru.digitalmagicians.ebito.dto.CreateAdsDto;
import ru.digitalmagicians.ebito.dto.FullAdsDto;
import ru.digitalmagicians.ebito.dto.ResponseWrapperAdsDto;


import java.util.List;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@Tag(name = "Объявления")
public class AdsController {

    @Operation(
            summary = "Добавить объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "201", description = "Created",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = AdsDto.class)
                                    )
                            }
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> setAds(@RequestPart("image") MultipartFile image,
                                         @RequestPart("properties") CreateAdsDto properties) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new AdsDto());
    }

    @Operation(
            summary = "Обновить информацию об объявлении",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = CreateAdsDto.class)
                                    )
                            }
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<CreateAdsDto> updateAds(@PathVariable("id") Long id, @RequestBody CreateAdsDto adsDto) {
        return ResponseEntity.ok(adsDto);
    }

    @Operation(
            summary = "Обновить картинку объявления",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "application/octet-stream"
                                    )
                            }
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateAdsImage(@PathVariable("id") Long id, @RequestPart("image") MultipartFile image) {
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Получить все объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ResponseWrapperAdsDto.class)
                                    )
                            }
                    )
            }
    )
    @GetMapping("/")
    public ResponseEntity<List<ResponseWrapperAdsDto>> getAll() {
        return ResponseEntity.ok(List.of(new ResponseWrapperAdsDto()));
    }

    @Operation(
            summary = "Получить объявления авторизованного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ResponseWrapperAdsDto.class)
                                    )
                            }
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @GetMapping("me")
    public ResponseEntity<ResponseWrapperAdsDto> getMe() {

        return ResponseEntity.ok(new ResponseWrapperAdsDto());
    }

    @Operation(
            summary = "Получить информацию об объявлении",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = FullAdsDto.class)
                                    )
                            }
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<FullAdsDto> getAdsById(@PathVariable("id") Long id) {

        return ResponseEntity.ok(new FullAdsDto());
    }

    @Operation(
            summary = "Удалить объявления",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No content"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAds(@PathVariable("id") Long id) {
        return ResponseEntity.ok().build();
    }

}
