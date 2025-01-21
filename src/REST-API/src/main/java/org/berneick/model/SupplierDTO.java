package org.berneick.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class SupplierDTO {
    private UUID supplierId;
    private String name;
    private UUID addressId;
    private String phoneNumber;
}
