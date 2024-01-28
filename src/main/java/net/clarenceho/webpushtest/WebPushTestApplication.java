package net.clarenceho.webpushtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class WebPushTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebPushTestApplication.class, args);
    }

    // open api for demo purpose. should limit CORS in actual production
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods(
                    RequestMethod.GET.name(),
                    RequestMethod.POST.name(),
                    RequestMethod.PUT.name(),
                    RequestMethod.DELETE.name());
            }
        };
    }
}
