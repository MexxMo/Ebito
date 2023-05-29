package ru.digitalmagicians.ebito.entity;

import lombok.Data;
import ru.digitalmagicians.ebito.dto.Role;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String image;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Ads> ads;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Comment> comments;

}
