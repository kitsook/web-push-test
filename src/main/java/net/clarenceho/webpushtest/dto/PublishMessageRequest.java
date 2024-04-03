package net.clarenceho.webpushtest.dto;

import org.springframework.lang.Nullable;

import java.util.Objects;

public record PublishMessageRequest(String title, @Nullable String message, @Nullable String icon, @Nullable String url) {
    public PublishMessageRequest {
        Objects.requireNonNull(title);
    }
}
