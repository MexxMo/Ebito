package ru.digitalmagicians.ebito.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "ads")
@Data
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private User author;

}
