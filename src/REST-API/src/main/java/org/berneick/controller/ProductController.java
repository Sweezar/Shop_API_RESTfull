package org.berneick.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.berneick.exception.InsufficientAvailableStockException;
import org.berneick.exception.ProductNotFoundException;
import org.berneick.exception.SupplierNotFoundException;
import org.berneick.model.ProductDTO;
import org.berneick.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Product API", description = "API для управления товарами")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    @Operation(summary = "Создание нового товара", description = "Создает новый товар с предоставленными данными по id поставщика.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Товар успешно создан"),
            @ApiResponse(responseCode = "404", description = "Поставщик не найден")
    })
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        try {
            ProductDTO createdProduct = productService.createProduct(productDTO);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (SupplierNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{productId}")
    @Operation(summary = "Уменьшение количества товара", description = "Уменьшает количество товара по id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Количество товара успешно изменено"),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе"),
            @ApiResponse(responseCode = "404", description = "Товар не найден")
    })
    public ResponseEntity<?> productReduction(
            @Parameter(description = "Id товара", example = "99b01137-6380-4684-a3f9-1ebaebc33060")
            @PathVariable UUID productId,
            @Parameter(description = "Разница, на которую нужно уменьшить", example = "1")
            @RequestParam int reduceCount
    ) {
        try {
            ProductDTO productDTO = productService.productReduction(productId, reduceCount);
            return new ResponseEntity<>(productDTO, HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        } catch (InsufficientAvailableStockException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Получение товара по id", description = "Возвращает информацию о товаре по его id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Товар найден"),
            @ApiResponse(responseCode = "404", description = "Товар с таким id не найден")
    })
    public ResponseEntity<?> findById(
            @Parameter(description = "Id товара", example = "99b01137-6380-4684-a3f9-1ebaebc33060")
            @PathVariable UUID productId
    ) {
        try {
            ProductDTO productDTO = productService.findById(productId);
            return new ResponseEntity<>(productDTO, HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @Operation(summary = "Получение всех товаров", description = "Возвращает информацию о товарах.")
    @ApiResponse(responseCode = "200", description = "Найденные товары")
    public ResponseEntity<List<ProductDTO>> findAll() {
        List<ProductDTO> products = productService.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "Удаление товара по id", description = "Удаляет товар по его id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Товар удален"),
            @ApiResponse(responseCode = "404", description = "Товар с таким id не найден")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "Id товара", example = "99b01137-6380-4684-a3f9-1ebaebc33060")
            @PathVariable UUID productId
    ) {
        try {
            productService.delete(productId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ProductNotFoundException e) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
