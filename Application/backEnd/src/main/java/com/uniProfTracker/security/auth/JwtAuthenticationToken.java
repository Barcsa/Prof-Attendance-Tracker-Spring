package com.licenta.security.auth;


import com.licenta.security.model.UserContext;
import com.licenta.security.model.token.RawAccessJwtToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * An {@link org.springframework.security.core.Authentication} implementation
 * that is designed for simple presentation of JwtToken.
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 8549185784975372626L;
    private transient RawAccessJwtToken rawAccessToken;
    private transient UserContext userContext;


    public JwtAuthenticationToken(RawAccessJwtToken unsafeToken) {
        super(null);
        this.rawAccessToken = unsafeToken;
        this.setAuthenticated(false);
    }

    public JwtAuthenticationToken(UserContext userContext, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.eraseCredentials();
        this.userContext = userContext;
        super.setAuthenticated(true);
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return rawAccessToken;
    }

    @Override
    public Object getPrincipal() {
        return this.userContext;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.rawAccessToken = null;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) obj;
            return this.rawAccessToken.getToken().equals(jwtAuthenticationToken.rawAccessToken.getToken());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
