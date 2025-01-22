package org.berneick.service;

import jakarta.annotation.Nonnull;
import org.berneick.exception.ImageNotFoundException;
import org.berneick.exception.ProductNotFoundException;
import org.berneick.mapper.ImageMapper;
import org.berneick.model.Image;
import org.berneick.model.ImageDTO;
import org.berneick.model.Product;
import org.berneick.repository.ImageRepository;
import org.berneick.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private ProductRepository productRepository;

    @Nonnull
    @Transactional
    public ImageDTO createImage(@Nonnull byte[] image, @Nonnull UUID productId) throws ProductNotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + productId));

        Image newImage = product.getImage();
        newImage.setImage(image);
        Image savedImage = imageRepository.save(newImage);

        return imageMapper.toDTO(savedImage);
    }

    @Nonnull
    @Transactional
    public ImageDTO updateImage(@Nonnull UUID imageId, @Nonnull byte[] image) {
        Image currentImage = imageRepository.findById(imageId)
                .orElseThrow(() -> new ImageNotFoundException("Image not found with id: " + imageId));

        currentImage.setImage(image);
        Image savedImage = imageRepository.save(currentImage);
        return imageMapper.toDTO(savedImage);
    }

    @Nonnull
    @Transactional(readOnly = true)
    public ImageDTO findById(@Nonnull UUID imageId) {
        Image currentImage = imageRepository.findById(imageId)
                .orElseThrow(() -> new ImageNotFoundException("Image not found with id: " + imageId));

        return imageMapper.toDTO(currentImage);
    }

    @Nonnull
    @Transactional(readOnly = true)
    public ImageDTO findByProductId(@Nonnull UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + productId));

        if(product.getImage() == null) {
            throw new ImageNotFoundException("Image not found for product with id: " + productId);
        }

        return imageMapper.toDTO(product.getImage());
    }

    @Transactional
    public void delete(@Nonnull UUID imageId) {
        if(imageId == null) {
            throw new IllegalArgumentException("ImageId must not be null");
        }

        if(!imageRepository.existsById(imageId)) {
            throw new ImageNotFoundException("Image not found with id: " + imageId);
        }

        imageRepository.deleteById(imageId);
    }
}
