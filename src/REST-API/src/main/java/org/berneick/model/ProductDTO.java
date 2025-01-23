package org.berneick.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Schema(description = "Модель товара")
public class ProductDTO {
    @Schema(description = "Уникальный идентификатор товара", example = "99b01137-6380-4684-a3f9-1ebaebc33060")
    private UUID productId;

    @NotBlank
    @Schema(description = "Название товара", example = "Холодильник")
    private String name;

    @NotBlank
    @Schema(description = "Категория товара", example = "Кухня")
    private String category;

    @NotNull
    @Min(value = 0, message = "price cannot be negative")
    @Schema(description = "Цена товара", example = "25000")
    private double price;

    @NotNull
    @Min(value = 0, message = "availableStock cannot be negative")
    @Schema(description = "Количество товара", example = "2")
    private int availableStock;

    @NotNull
    @Schema(description = "Последнее обновление товара", example = "2025-01-23T08:49:30")
    private LocalDateTime lastUpdateDate;

    @NotNull
    @Schema(description = "Уникальный идентификатор поставщика", example = "99b01137-6380-4684-a3f9-1ebaebc33060")
    private UUID supplierId;

    @Schema(description = "Уникальный идентификатор изображения товара", example = "99b01137-6380-4684-a3f9-1ebaebc33060")
    private UUID imageId;
}
