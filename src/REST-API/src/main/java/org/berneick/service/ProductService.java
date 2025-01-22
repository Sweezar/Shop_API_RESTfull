package org.berneick.service;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.berneick.exception.InsufficientAvailableStockException;
import org.berneick.exception.ProductNotFoundException;
import org.berneick.exception.SupplierNotFoundException;
import org.berneick.mapper.ProductMapper;
import org.berneick.model.Product;
import org.berneick.model.ProductDTO;
import org.berneick.model.Supplier;
import org.berneick.repository.ProductRepository;
import org.berneick.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductMapper productMapper;

    @Nonnull
    @Transactional
    public ProductDTO createProduct(@Nonnull ProductDTO productDTO) {
        Supplier supplier = supplierRepository.findById(productDTO.getSupplierId())
                .orElseThrow(() -> new SupplierNotFoundException("Supplier not found with id: " + productDTO.getSupplierId()));

        Product product = productMapper.toEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    @Nonnull
    @Transactional
    public ProductDTO productReduction(@Nonnull UUID productId, int reduceCount) throws ProductNotFoundException, InsufficientAvailableStockException {
        if(reduceCount <= 0) {
            throw new IllegalArgumentException("reduceCount must be a positive number");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + productId));


        if(product.getAvailableStock() < reduceCount) {
            throw new InsufficientAvailableStockException("Insufficient Available Stock");
        }
        product.setAvailableStock(product.getAvailableStock() - reduceCount);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    @Nonnull
    @Transactional(readOnly = true)
    public ProductDTO findById(@Nonnull UUID productId) throws ProductNotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + productId));
        return productMapper.toDTO(product);
    }

    @Nonnull
    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Transactional
    public void delete(@Nonnull UUID productId) throws ProductNotFoundException {
        if(!productRepository.existsById(productId)) {
            throw new ProductNotFoundException("Product not found with id " + productId);
        }

        productRepository.deleteById(productId);
    }

}
