package pl.app.mail.mail.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.mail.mail.application.port.in.SendMailCommand;
import pl.app.mail.mail.application.port.in.SendMailUseCase;

@RestController
@RequestMapping(MailController.resourcePath)
@RequiredArgsConstructor
@Getter
class MailController {
    public static final String resourceName = "mail";
    public static final String resourcePath = "/api/v1/" + resourceName;
    public final SendMailUseCase sendMailUseCase;

    @PostMapping(path = "send-mail")
    private ResponseEntity<Void> sendMail(@RequestBody SendMailCommand command) {
        sendMailUseCase.sendMail(command);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }
}
