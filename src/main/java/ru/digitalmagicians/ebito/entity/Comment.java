package ru.digitalmagicians.ebito.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private Long createdAt;
    @NotNull
    private String text;
    @ManyToOne()
    private Ads ads;
    @ManyToOne()
    private User author;
}
