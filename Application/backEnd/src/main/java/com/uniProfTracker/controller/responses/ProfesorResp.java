package com.licenta.controller.responses;

import com.licenta.model.Profesor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ProfesorResp {
    private long id;

    private String name;

    private String mail;

    private String phone;

    public static ProfesorResp fromEntity(Profesor profesor) {
        ProfesorResp resp = new ProfesorResp();
        resp.setId(profesor.getId());
        resp.setName(profesor.getName());
        resp.setPhone(profesor.getPhone());
        resp.setMail(profesor.getMail());

        return resp;
    }
}
