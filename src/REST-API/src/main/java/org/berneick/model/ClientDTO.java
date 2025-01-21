package org.berneick.model;

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
public class ClientDTO {
    private UUID clientId;

    @NotBlank
    private String clientName;

    @NotBlank
    private String clientSurname;

    @NotNull
    private LocalDate birthday;

    @NotBlank
    private String gender;

    @NotNull
    private LocalDateTime registrationDate;
    private UUID addressId;
}
