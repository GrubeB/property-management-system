package pl.app.mail.template.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;


@Getter
@AllArgsConstructor
public class TemplateAttachment {
    private String attachmentId;
    private String name;
    private String fileId;

    public TemplateAttachment(String name, String fileId) {
        this.attachmentId = UUID.randomUUID().toString();
        this.name = name;
        this.fileId = fileId;
    }
}
