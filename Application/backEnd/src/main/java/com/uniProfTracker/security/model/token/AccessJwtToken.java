package com.licenta.security.model.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.Claims;

/**
 * Raw representation of JWT Token.
 */
public final class AccessJwtToken implements JwtToken {

    private static final long serialVersionUID = 4066679551938232737L;

    private final String rawToken;
    @JsonIgnore
    private transient Claims claims;

    protected AccessJwtToken(final String token, Claims claims) {
        this.rawToken = token;
        this.claims = claims;
    }

    @Override
    public String getToken() {
        return this.rawToken;
    }

    public Claims getClaims() {
        return claims;
    }
}
