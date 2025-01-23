package org.berneick.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Schema(description = "Модель клиента")
public class ClientDTO {
    @Schema(description = "Уникальный идентификатор клиента", example = "99b01137-6380-4684-a3f9-1ebaebc33060")
    private UUID clientId;

    @NotBlank
    @Schema(description = "Имя клиента", example = "Иван")
    private String clientName;

    @NotBlank
    @Schema(description = "Фамилия клиента", example = "Иванов")
    private String clientSurname;

    @NotNull
    @Schema(description = "Дата рождения клиента", example = "2025-01-23")
    private LocalDate birthday;

    @NotBlank
    @Schema(description = "Пол клиента", example = "male")
    private String gender;

    @NotNull
    @Schema(description = "Дата регистрации клиента", example = "2025-01-23T08:49:30")
    private LocalDateTime registrationDate;

    @Schema(description = "Адрес клиента", example = "99b01137-6380-4684-a3f9-1ebaebc33061")
    private UUID addressId;
}
