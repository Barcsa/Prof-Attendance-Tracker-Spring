package com.licenta.controller.requestbody;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EditUserRequest {
    @NotNull
    private String name;

    private String surname;

    private String mobile;
}
