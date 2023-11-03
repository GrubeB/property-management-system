package pl.app.mail.template.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class TemplateResource {
    private String resourceId;
    private String name;
    private String fileId;

    public TemplateResource(String name, String fileId) {
        this.resourceId = UUID.randomUUID().toString();
        this.name = name;
        this.fileId = fileId;
    }
}
