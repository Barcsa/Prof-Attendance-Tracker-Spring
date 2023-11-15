package com.licenta;

import lombok.Getter;

import java.util.Date;

/**
 *  Model for interacting with client.
 */
@Getter
public class OperationResponse {
    private final String message;

    private final Long entityId;

    private final Date timestamp;

    protected OperationResponse(final String message, final Long entityId) {
        this.message = message;
        this.entityId = entityId;
        this.timestamp = new Date();
    }

    public static OperationResponse of(final String message) {
        return new OperationResponse(message, null);
    }

    public static OperationResponse of(final String message, final Long entityId) {
        return new OperationResponse(message, entityId);
    }

}