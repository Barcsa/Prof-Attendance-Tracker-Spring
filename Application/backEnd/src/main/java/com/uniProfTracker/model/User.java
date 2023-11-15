package com.licenta.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
public class User extends BaseEntity {
    private String name;

    private String surname;

    private String email;

    private String mobile;

    private String password;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private List<UserRole> roles = new ArrayList<>();

    /**
     * null if user is not also profesor
     */
    @OneToOne
    private Profesor profesor;

    final public List<Role> rawRoles() {
        return roles.stream().map(UserRole::getRole).collect(Collectors.toList());
    }
}
