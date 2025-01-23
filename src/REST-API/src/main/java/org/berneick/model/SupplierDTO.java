package org.berneick.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@Schema(description = "Модель поставщика")
public class SupplierDTO {
    @Schema(description = "Уникальный идентификатор поставщика", example = "99b01137-6380-4684-a3f9-1ebaebc33060")
    private UUID supplierId;

    @NotBlank
    @Schema(description = "Название поставщика", example = "Новая техника")
    private String name;

    @Schema(description = "Уникальный идентификатор адреса поставщика", example = "99b01137-6380-4684-a3f9-1ebaebc33061")
    private UUID addressId;

    @NotBlank
    @Schema(description = "Номер телефона поставщика", example = "999-123-456")
    private String phoneNumber;
}
