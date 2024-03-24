package net.clarenceho.webpushtest.utils;

import nl.martijndwars.webpush.Subscription;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MemoryStorage implements Storage {

    // subscription should be stored in persistent storage
    private final Map<String, Subscription> subscriptions = new ConcurrentHashMap<>();

    @Override
    public void createOrUpdateSubscription(Subscription subscription) {
        subscriptions.put(subscription.endpoint, subscription);
    }

    @Override
    public void removeSubscription(String endpoint) {
        subscriptions.remove(endpoint);
    }

    @Override
    public Collection<Subscription> getSubscriptions() {
        return subscriptions.values();
    }
}
