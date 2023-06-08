package ru.digitalmagicians.ebito.entity;

import lombok.*;
import org.hibernate.annotations.Type;


import javax.persistence.*;


@Data
@Entity
@Table(name = "images")
public class Image {
    @Id
    private String id;

    @Lob
    @Column(name = "image")
    @Type(type = "binary")
    private byte[] image;

}
