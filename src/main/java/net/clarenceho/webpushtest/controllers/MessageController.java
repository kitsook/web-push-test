package net.clarenceho.webpushtest.controllers;

import com.google.gson.Gson;
import net.clarenceho.webpushtest.models.NotificationMessage;
import net.clarenceho.webpushtest.models.PublishMessageRequest;
import net.clarenceho.webpushtest.services.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    @Autowired
    private MessageService msgService;

    private final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @PostMapping(value = "/message", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> subscribe(@RequestBody PublishMessageRequest request) {
        if (logger.isInfoEnabled()) {
            logger.info("new message to publish: {}", request.message());
        }

        msgService.pushNotification(new Gson().toJson(generateMessage(request)));
        return new ResponseEntity<>("{result: ok}", HttpStatus.OK);
    }

    private NotificationMessage generateMessage(PublishMessageRequest request) {
        return new NotificationMessage(request.title(), request.message(), request.icon(), request.url());
    }
}