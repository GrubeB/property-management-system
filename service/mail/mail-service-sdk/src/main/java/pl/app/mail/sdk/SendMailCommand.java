package pl.app.mail.sdk;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendMailCommand implements Serializable {
    private String to;
    private String subject;
    private String templateName;
    private String applicationId;
    private List<Property> properties;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Property {
        private String name;
        private String value;
    }

}
