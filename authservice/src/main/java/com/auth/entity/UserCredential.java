package com.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor


@Table(name="user")
public class UserCredential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private String name;


    private String profilePicture ="https://upload.wikimedia.org/wikipedia/commons/a/ac/Default_pfp.jpg";

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonIgnore
    private Set<Role> role;
    @Column(unique = true)
    private String email;

    private String password;
    private Long phno;
    private String gender;

    @JsonIgnore
    private String status;
    @JsonIgnore
    private LocalTime timeStamp;


}