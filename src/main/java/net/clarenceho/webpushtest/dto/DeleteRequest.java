package net.clarenceho.webpushtest.dto;

import java.util.Objects;

public record DeleteRequest(String endpoint) {
    public DeleteRequest {
        Objects.requireNonNull(endpoint);
    }
}
