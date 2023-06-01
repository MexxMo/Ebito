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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.digitalmagicians.ebito.dto.AdsDto;
import ru.digitalmagicians.ebito.dto.CreateAdsDto;
import ru.digitalmagicians.ebito.dto.FullAdsDto;
import ru.digitalmagicians.ebito.dto.ResponseWrapperAdsDto;
import ru.digitalmagicians.ebito.service.AdsService;


@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@Tag(name = "Объявления")
@RequiredArgsConstructor
public class AdsController {
    private final AdsService adsService;

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
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> setAds(@RequestPart("image") MultipartFile image,
                                         @RequestPart("properties") CreateAdsDto properties, Authentication authentication) {

        return ResponseEntity.status(HttpStatus.CREATED).body(adsService.createAds(image, properties,authentication));
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
    public ResponseEntity<CreateAdsDto> updateAds(@PathVariable("id") Integer id, @RequestBody CreateAdsDto adsDto) {
        return ResponseEntity.ok(adsService.updateAds(id, adsDto));
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
    @GetMapping()
    public ResponseEntity<ResponseWrapperAdsDto> getAll() {
        return ResponseEntity.ok(adsService.getAll());
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
    public ResponseEntity<ResponseWrapperAdsDto> getMe(Authentication authentication) {

        return ResponseEntity.ok(adsService.getAllByMe(authentication));

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
    public ResponseEntity<FullAdsDto> getAdsById(@PathVariable("id") Integer id) {

        return ResponseEntity.ok(adsService.getById(id));
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
    public ResponseEntity<?> deleteAds(@PathVariable("id") Integer id) {
        adsService.delete(id);
        return ResponseEntity.ok().build();
    }

}
