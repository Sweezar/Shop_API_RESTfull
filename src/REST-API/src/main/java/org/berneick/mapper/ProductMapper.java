package org.berneick.mapper;

import org.berneick.model.Image;
import org.berneick.model.Product;
import org.berneick.model.ProductDTO;
import org.berneick.model.Supplier;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .productId(product.getId())
                .name(product.getName())
                .category(product.getCategory())
                .price(product.getPrice())
                .availableStock(product.getAvailableStock())
                .lastUpdateDate(product.getLastUpdateDate())
                .supplierId(product.getSupplier().getId())
//                .imageId(product.getImage().getId())
                .build();
    }

    public Product toEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setCategory(productDTO.getCategory());
        product.setPrice(productDTO.getPrice());
        product.setAvailableStock(productDTO.getAvailableStock());
        product.setLastUpdateDate(productDTO.getLastUpdateDate());

        Supplier supplier = new Supplier();
        supplier.setId(productDTO.getSupplierId());
        product.setSupplier(supplier);

        if(productDTO.getImageId() != null) {
            Image image = new Image();
            image.setId(productDTO.getImageId());
            product.setImage(image);
        }

        return product;
    }
}
