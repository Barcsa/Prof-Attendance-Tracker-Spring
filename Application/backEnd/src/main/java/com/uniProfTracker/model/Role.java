package com.licenta.model;

/**
 * Enumerated {@link User} roles.
 */
public enum Role {
    ADMIN, PROFESOR;

    public String authority() {
        return "ROLE_" + this.name();
    }
}
