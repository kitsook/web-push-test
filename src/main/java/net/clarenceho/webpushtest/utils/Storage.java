package net.clarenceho.webpushtest.utils;

import nl.martijndwars.webpush.Subscription;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Storage {
    private Storage() {}

    // these keys should be stored in secured storage or environment
    public static final String PUBLIC_KEY = "BGgL7I82SAQM78oyGwaJdrQFhVfZqL9h4Y18BLtgJQ-9pSGXwxqAWQudqmcv41RcWgk1ssUeItv4-8khxbhYveM=";
    public static final String PRIVATE_KEY = "ANlfcVVFB4JiMYcI74_h9h04QZ1Ks96AyEa1yrMgDwn3";

    // subscription should be stored in persistent storage
    private static final Map<String, Subscription> subscriptions = new ConcurrentHashMap<>();

    public static void addSubscription(Subscription subscription) {
        subscriptions.put(subscription.endpoint, subscription);
    }

    public static void removeSubscription(String endpoint) {
        subscriptions.remove(endpoint);
    }

    public static Collection<Subscription> getSubscriptions() {
        return subscriptions.values();
    }
}
