package com.licenta.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Evidenta extends BaseEntity {

    @ManyToOne
    @JoinColumn(columnDefinition = "curs_id")
    private Curs curs;

    private boolean deleted;

    @ManyToOne
    @JoinColumn(columnDefinition = "profesor_id")
    private Profesor profesor;

    private String data;

    private String intreOre;

    private String nrOreCurs;

    private String nrOreSLP;
}
