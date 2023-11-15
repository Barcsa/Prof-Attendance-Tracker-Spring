package com.licenta.security.auth.ajax;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * Model intended to be used for AJAX based authentication.
 */
@Getter
public class LoginRequest {
    private String email;
    private String password;

    @JsonCreator
    public LoginRequest(@JsonProperty("username") String email, @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
    }

}
