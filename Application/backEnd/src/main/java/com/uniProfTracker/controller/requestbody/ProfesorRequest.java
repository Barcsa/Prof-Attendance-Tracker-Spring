package com.licenta.controller.requestbody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfesorRequest {

    private String name;

    private String mail;

    private String phone;

    private String password;

}
