package org.berneick.mapper;

import org.berneick.model.Image;
import org.berneick.model.ImageDTO;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {
    public ImageDTO toDTO(Image image) {
        return new ImageDTO(image.getId(), image.getImage());
    }

    public Image toEntity(ImageDTO imageDTO) {
        Image image = new Image();
        image.setId(imageDTO.getImageId());
        image.setImage(imageDTO.getImage());

        return image;
    }
}
