package com.licenta.controller.responses;

import com.licenta.model.Evidenta;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class EvidentaResponse {

    private long id;

    private boolean deleted;

    private String cursName;

    private String an;

    private String profesorName;

    private String data;

    private String intreOre;

    private String nrOreCurs;

    private String nrOreSLP;


    public static EvidentaResponse fromEntity(Evidenta evidenta) {
        EvidentaResponse resp = new EvidentaResponse();
        resp.setId(evidenta.getId());
        resp.setData(evidenta.getData());
        resp.setDeleted(evidenta.isDeleted());
        resp.setIntreOre(evidenta.getIntreOre());
        resp.setProfesorName(ProfesorResp.fromEntity(evidenta.getProfesor()).getName());
        resp.setCursName(CursResp.fromEntity(evidenta.getCurs()).getNumeCurs());
        resp.setAn(CursResp.fromEntity(evidenta.getCurs()).getAn());
        resp.setNrOreSLP(evidenta.getNrOreSLP());
        resp.setNrOreCurs(evidenta.getNrOreCurs());

        return resp;
    }

}
