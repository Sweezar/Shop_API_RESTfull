package org.berneick.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ImageDTO {
    private UUID imageId;
    private byte[] image;
}
