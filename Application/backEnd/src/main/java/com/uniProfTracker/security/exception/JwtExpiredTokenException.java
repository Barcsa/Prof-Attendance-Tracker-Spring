package com.licenta.security.exception;

import com.licenta.security.model.token.JwtToken;
import org.springframework.security.core.AuthenticationException;


public class JwtExpiredTokenException extends AuthenticationException {
    private static final long serialVersionUID = 1144366365029521005L;

    private final JwtToken token;

    public JwtExpiredTokenException(JwtToken token, String msg, Throwable throwable) {
        super(msg, throwable);
        this.token = token;
    }

    public String token() {
        return this.token.getToken();
    }
}
