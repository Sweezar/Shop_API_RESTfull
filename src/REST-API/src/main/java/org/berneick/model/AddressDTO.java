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
@Schema(description = "Модель адреса")
public class AddressDTO {
    @Schema(description = "Уникальный идентификатор адреса", example = "99b01137-6380-4684-a3f9-1ebaebc33060" )
    private UUID addressId;

    @NotBlank
    @Schema(description = "Страна", example = "Россия")
    private String country;

    @NotBlank
    @Schema(description = "Город", example = "Казань")
    private String city;

    @NotBlank
    @Schema(description = "Улица", example = "Ленина")
    private String street;
}
