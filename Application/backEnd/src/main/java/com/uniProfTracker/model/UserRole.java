package com.licenta.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/*
 * UserRole
 */
@Entity
@Table(name = "USER_ROLE")
@Getter
@Setter
@NoArgsConstructor
public class UserRole {
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    protected Role role;
    @ManyToOne
    protected User user;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public UserRole(final User user, final Role role) {
        this.user = user;
        this.role = role;
    }
}
