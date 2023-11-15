package com.licenta.controller.requestbody;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordRequest {
    private String email;
    private String pass1;
    private String pass2;
}
