package com.licenta.controller.requestbody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CursRequest {

    private String numeCurs;

    private String an;

    private String specializare;

    private long profesorId;
}
