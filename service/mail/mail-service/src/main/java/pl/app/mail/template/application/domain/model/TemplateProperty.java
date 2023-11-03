package pl.app.mail.template.application.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class TemplateProperty {
    private String propertyId;
    private String name;
    private String defaultValue;

    public TemplateProperty(String name, String defaultValue) {
        this.propertyId = UUID.randomUUID().toString();
        this.name = name;
        this.defaultValue = defaultValue;
    }
}
