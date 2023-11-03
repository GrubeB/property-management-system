package pl.app.mail.mail.application.port.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SendMailCommand implements Serializable {
    @NotBlank
    @Email
    private String to;
    @NotBlank
    private String subject;
    @NotBlank
    private String templateName;
    @NotNull
    private String applicationId;
    @Valid
    private List<Property> properties;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Property {
        @NotBlank
        private String name;
        @NotNull
        private String value;
    }
}
