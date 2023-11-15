package com.licenta.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class Profesor extends BaseEntity {

    private String name;

    private String mail;

    private String phone;

    private boolean deleted;

    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL)
    private List<Curs> cursuri;

    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL)
    private List<Evidenta> evidentaList;
}
