package net.clarenceho.webpushtest;

import net.clarenceho.webpushtest.utils.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Autowired
    private ApplicationContext context;

    @Bean
    public Storage appStorage(@Value("${app.config.storage}") String qualifier) {
        return (Storage) context.getBean(qualifier);
    }
}
