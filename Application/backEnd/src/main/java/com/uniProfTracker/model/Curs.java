package com.licenta.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Curs extends BaseEntity {

    private boolean deleted;

    private String numeCurs;

    private String an;

    private String specializare;

    @ManyToOne
    @JoinColumn(columnDefinition = "profesor_id")
    private Profesor profesor;

    @OneToMany(mappedBy = "curs", cascade = CascadeType.ALL)
    private List<Evidenta> evidentaList;

}
