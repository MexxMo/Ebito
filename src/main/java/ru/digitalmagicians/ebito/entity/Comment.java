package ru.digitalmagicians.ebito.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private User author;

}
