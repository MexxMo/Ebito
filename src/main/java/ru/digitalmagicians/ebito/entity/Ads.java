package ru.digitalmagicians.ebito.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ads")
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private int price;

    @ManyToOne()
    private User author;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image")
    private Image image;
}
