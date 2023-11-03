package pl.app.mail.mail.application.port.in;

import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Validated
public interface SendMailUseCase {
    void sendMail(final String to, final String subject, final String template, final Map<String, Object> templateModel, final Map<String, Resource> resourceMap, final Map<String, Resource> attachmentMap);

    void sendMail(@Valid SendMailCommand command);
}
