package com.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Time;
import java.time.LocalTime;
import java.util.*;

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
    private Set<Role> role = new HashSet<>();

    @JsonProperty("role")  // This will override the default "roles" field with a single "role"
    public String getSingleRole() {
        return role.stream()
                .findFirst() // Get the first role in the set
                .map(Role::getRole)
                .orElse(null);  // Return null if no roles are present
    }
    @JsonProperty("role")
    public void setSingleRole(String singleRole) {
        if (singleRole != null && !singleRole.isEmpty()) {
            Role role = new Role();
            role.setRole(singleRole);
            this.role.add(role); // Add the role to the set
        }
    }
    @Column(unique = true)
    private String email;

    private String password;
    private Long phno;
    private String gender;





}