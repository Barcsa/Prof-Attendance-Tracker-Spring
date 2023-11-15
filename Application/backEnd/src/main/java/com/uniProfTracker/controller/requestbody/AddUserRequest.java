package com.licenta.controller.requestbody;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddUserRequest {
    @NotNull
    private String name;

    private String surname;

    @NotNull
    private String email;

    private String mobile;

    @NotNull
    private String password;
}
