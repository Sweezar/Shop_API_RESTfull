package org.berneick.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class AddressDTO {
    private UUID addressId;
    @NotBlank
    private String country;

    @NotBlank
    private String city;

    @NotBlank
    private String street;
}
