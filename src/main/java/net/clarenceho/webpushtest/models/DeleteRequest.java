package net.clarenceho.webpushtest.models;

import java.util.Objects;

public record DeleteRequest(String endpoint) {
    public DeleteRequest {
        Objects.requireNonNull(endpoint);
    }
}
