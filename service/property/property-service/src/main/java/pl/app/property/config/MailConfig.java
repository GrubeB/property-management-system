package pl.app.property.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.app.mail.sdk.SendMailHttpClient;

import java.net.http.HttpClient;

@Configuration
public class MailConfig {
    @Value("${app.mail.base-url:http://localhost:8888}")
    private String sendMailServiceURI;
    @Value("${spring.application.name:}")
    private String applicationName;

    @Bean
    public SendMailHttpClient sendMailHttpClient() {
        return new SendMailHttpClient(
                new ObjectMapper(),
                HttpClient.newBuilder().build(),
                sendMailServiceURI,
                applicationName
        );
    }
}