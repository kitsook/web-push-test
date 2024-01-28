package net.clarenceho.webpushtest.controllers;

import net.clarenceho.webpushtest.models.DeleteRequest;
import net.clarenceho.webpushtest.utils.Storage;
import nl.martijndwars.webpush.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class SubscriptionController {

    private static final PublicKey PUBLIC_KEY = new PublicKey(Storage.PUBLIC_KEY);
    private final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);

    @PostMapping(value = "/subscription", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> subscribe(@RequestBody Subscription subscription) {
        if (logger.isInfoEnabled()) {
            logger.info("new subscription: {}", subscription.endpoint);
        }
        Storage.addSubscription(subscription);
        return new ResponseEntity<>("{result: ok}", HttpStatus.OK);
    }

    @DeleteMapping(value = "/subscription", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> unsubscribe(@RequestBody DeleteRequest request) {
        Storage.removeSubscription(request.endpoint());
        return new ResponseEntity<>("{result: ok}", HttpStatus.OK);
    }

    @GetMapping(value = "/subscription/pubkey", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> publicKey() {
        return new ResponseEntity<>(PUBLIC_KEY, HttpStatus.OK);
    }

    private record PublicKey(String publicKey) {
        PublicKey {
            Objects.requireNonNull(publicKey);
        }
    }
}
