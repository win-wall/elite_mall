package com.mall.elite.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    //@Column(unique = true, nullable = false)
    private int id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Email
    @NotBlank(message = "Email is mandatory")
    private String email;
    @ManyToMany(cascade = {CascadeType.ALL})
    private Collection<Role> roles = new ArrayList<>();
}
