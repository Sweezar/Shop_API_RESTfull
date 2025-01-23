package org.berneick.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "images")
@Getter
@Setter
@DynamicInsert
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Lob
    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "image", columnDefinition = "bytea", nullable = false)
    private byte[] image;
}
