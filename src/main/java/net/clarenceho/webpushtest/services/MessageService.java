package net.clarenceho.webpushtest.services;

import jakarta.annotation.PostConstruct;
import net.clarenceho.webpushtest.utils.Storage;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import org.apache.http.HttpResponse;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.concurrent.ExecutionException;

@Service
public class MessageService {
    @Autowired
    @Qualifier("appStorage")
    Storage storage;

    private final Logger logger = LoggerFactory.getLogger(MessageService.class);
    private PushService pushService;

    @PostConstruct
    void init() throws GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider());
        pushService = new PushService(Storage.PUBLIC_KEY, Storage.PRIVATE_KEY, "mailto:test@example.com");
    }

    public void pushNotification(String messageJson) {
        storage.getSubscriptions().forEach(subscription -> sendNotification(subscription, messageJson));
    }

    private void sendNotification(Subscription subscription, String messageJson) {
        try {
            HttpResponse response = pushService.send(new Notification(subscription, messageJson));
            if (logger.isInfoEnabled()) {
                logger.info(response.toString());
            }
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 404 || statusCode == 410) {
                if (logger.isInfoEnabled()) {
                    logger.info("Unsubscribing {}", subscription.endpoint);
                }
                storage.removeSubscription(subscription.endpoint);
            } else if (statusCode >= 400 && statusCode <= 499) {
                logger.error("Problem publishing: {}", response.getStatusLine().getReasonPhrase());
            }
        } catch (GeneralSecurityException | IOException | JoseException | ExecutionException e) {
            logger.error("Failed to push notification", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
