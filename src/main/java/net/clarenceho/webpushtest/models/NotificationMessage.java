package net.clarenceho.webpushtest.models;

import org.springframework.lang.Nullable;

import java.util.Objects;

public record NotificationMessage(String title, @Nullable String message, @Nullable String icon, @Nullable String url) {
    public NotificationMessage {
        Objects.requireNonNull(title);
    }
}
