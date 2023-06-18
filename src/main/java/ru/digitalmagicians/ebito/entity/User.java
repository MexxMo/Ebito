package ru.digitalmagicians.ebito.entity;

import lombok.*;
import ru.digitalmagicians.ebito.dto.Role;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_avatar")
    private Image image;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Ads> ads;
}
