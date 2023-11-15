package com.licenta.security.model.token;

import java.io.Serializable;

@FunctionalInterface
public interface JwtToken extends Serializable {
    String getToken();
}
