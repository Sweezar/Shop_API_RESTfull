package org.berneick.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ProductDTO {
    private UUID productId;
    private String name;
    private String category;
    private double price;
    private int availableStock;
    private LocalDateTime lastUpdateDate;
    private UUID supplierId;
    private UUID imageId;
}
