package net.clarenceho.webpushtest.utils;

import nl.martijndwars.webpush.Subscription;

import java.util.Collection;

public interface Storage {
    // these keys should be stored in secured storage or environment
    String PUBLIC_KEY = "BGgL7I82SAQM78oyGwaJdrQFhVfZqL9h4Y18BLtgJQ-9pSGXwxqAWQudqmcv41RcWgk1ssUeItv4-8khxbhYveM=";
    String PRIVATE_KEY = "ANlfcVVFB4JiMYcI74_h9h04QZ1Ks96AyEa1yrMgDwn3";

    void createOrUpdateSubscription(Subscription subscription);

    void removeSubscription(String endpoint);

    Collection<Subscription> getSubscriptions();
}
