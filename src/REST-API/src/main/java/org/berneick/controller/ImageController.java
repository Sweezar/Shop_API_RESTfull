package org.berneick.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.berneick.exception.ImageNotFoundException;
import org.berneick.exception.ProductNotFoundException;
import org.berneick.model.ImageDTO;
import org.berneick.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/images")
@Tag(name = "Image API", description = "API для управления изображениями")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PostMapping("/{productId}")
    @Operation(summary = "Создание нового изображения", description = "Создает новое изображение по id товара.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Изображение успешно создано"),
            @ApiResponse(responseCode = "404", description = "Товар не найден"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
    })
    public ResponseEntity<?> createImage(
            @Parameter(description = "Файл с изображением")
            @RequestParam("file") MultipartFile file,
            @Parameter(description = "Id товара", example = "99b01137-6380-4684-a3f9-1ebaebc33060")
            @PathVariable UUID productId
    ) {
        try {
            byte[] image = file.getBytes();
            ImageDTO createdImage = imageService.createImage(image, productId);
            return new ResponseEntity<>(createdImage, HttpStatus.CREATED);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to process image", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    @Operation(summary = "Изменение изображения", description = "Изменяет изображение по id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Изображение успешно изменено"),
            @ApiResponse(responseCode = "404", description = "Изображение не найдено"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
    })
    public ResponseEntity<?> updateImage(
            @Parameter(description = "Id изображения", example = "99b01137-6380-4684-a3f9-1ebaebc33060")
            @RequestParam UUID imageId,
            @Parameter(description = "Файл с изображением")
            @RequestParam("file") MultipartFile file
    ) {
        try {
            byte[] image = file.getBytes();
            ImageDTO updatedImage = imageService.updateImage(imageId, image);
            return new ResponseEntity<>(updatedImage, HttpStatus.OK);
        } catch (ImageNotFoundException e) {
            return new ResponseEntity<>("Image not found", HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to process image", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/by-product/{productId}")
    @Operation(summary = "Получение изображения по id товара", description = "Загружает изображение по id товара.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Изображение успешно получено"),
            @ApiResponse(responseCode = "404", description = "Товар не найден")
    })
    public ResponseEntity<?> getImageByProductId(
            @Parameter(description = "Id товара")
            @PathVariable UUID productId
    ) {
        try {
            byte[] imageData = imageService.findByProductId(productId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "image_" + productId + ".jpeg");

            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-image/{imageId}")
    @Operation(summary = "Получение изображения по id", description = "Загружает изображение по id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Изображение успешно получено"),
            @ApiResponse(responseCode = "404", description = "Изображение не найдено")
    })
    public ResponseEntity<?> getImageById(
            @Parameter(description = "Id изображения")
            @PathVariable UUID imageId
    ) {
        try {
            byte[] imageData = imageService.findById(imageId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "image_" + imageId + ".jpeg");

            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } catch (ImageNotFoundException e) {
            return new ResponseEntity<>("Image not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{imageId}")
    @Operation(summary = "Удаление изображения по id", description = "Удаляет изображение по id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Изображение успешно удалено"),
            @ApiResponse(responseCode = "404", description = "Изображение не найдено")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "Id изображения")
            @PathVariable UUID imageId
    ) {
        try {
            imageService.delete(imageId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ImageNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
