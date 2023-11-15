package com.licenta.controller.responses;

import com.licenta.model.Curs;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CursResp {
    private long id;

    private boolean deleted;

    private String numeCurs;

    private String an;

    private String specializare;

    private String profesorName;

    private ProfesorResp profesorResp;


    public static CursResp fromEntity(Curs curs) {
        CursResp result = new CursResp();
        result.setId(curs.getId());
        result.setProfesorResp(ProfesorResp.fromEntity(curs.getProfesor()));
        result.setProfesorName(ProfesorResp.fromEntity(curs.getProfesor()).getName());
        result.setAn(curs.getAn());
        result.setDeleted(curs.isDeleted());
        result.setNumeCurs(curs.getNumeCurs());
        result.setSpecializare(curs.getSpecializare());
        return result;
    }
}
