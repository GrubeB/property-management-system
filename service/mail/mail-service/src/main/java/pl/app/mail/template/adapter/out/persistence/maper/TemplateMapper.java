package pl.app.mail.template.adapter.out.persistence.maper;

import org.springframework.stereotype.Component;
import pl.app.mail.template.adapter.out.persistence.model.TemplateAttachmentEntity;
import pl.app.mail.template.adapter.out.persistence.model.TemplateEntity;
import pl.app.mail.template.adapter.out.persistence.model.TemplatePropertyEntity;
import pl.app.mail.template.adapter.out.persistence.model.TemplateResourceEntity;
import pl.app.mail.template.application.domain.model.Template;
import pl.app.mail.template.application.domain.model.TemplateAttachment;
import pl.app.mail.template.application.domain.model.TemplateProperty;
import pl.app.mail.template.application.domain.model.TemplateResource;

import java.util.stream.Collectors;

@Component
public class TemplateMapper {
    public Template map(TemplateEntity entity) {
        return new Template(
                entity.getTemplateId(),
                entity.getName(),
                entity.getApplicationId(),
                entity.getTemplateFileId(),
                entity.getProperties().stream().map(this::map).collect(Collectors.toSet()),
                entity.getResources().stream().map(this::map).collect(Collectors.toSet()),
                entity.getAttachments().stream().map(this::map).collect(Collectors.toSet())
        );
    }

    public TemplateProperty map(TemplatePropertyEntity entity) {
        return new TemplateProperty(
                entity.getPropertyId(),
                entity.getName(),
                entity.getDefaultValue());
    }

    public TemplateResource map(TemplateResourceEntity entity) {
        return new TemplateResource(
                entity.getResourceId(),
                entity.getName(),
                entity.getFileId());
    }

    public TemplateAttachment map(TemplateAttachmentEntity entity) {
        return new TemplateAttachment(
                entity.getAttachmentId(),
                entity.getName(),
                entity.getFileId());
    }

    public TemplateEntity map(Template domain) {
        return TemplateEntity.builder()
                .templateId(domain.getTemplateId())
                .name(domain.getName())
                .applicationId(domain.getApplicationId())
                .templateFileId(domain.getTemplateFileId())
                .properties(domain.getProperties().stream().map(this::map).collect(Collectors.toSet()))
                .resources(domain.getResources().stream().map(this::map).collect(Collectors.toSet()))
                .attachments(domain.getAttachments().stream().map(this::map).collect(Collectors.toSet()))
                .build();
    }

    public TemplatePropertyEntity map(TemplateProperty domain) {
        return TemplatePropertyEntity.builder()
                .propertyId(domain.getPropertyId())
                .name(domain.getName())
                .defaultValue(domain.getDefaultValue())
                .build();
    }

    public TemplateResourceEntity map(TemplateResource domain) {
        return TemplateResourceEntity.builder()
                .resourceId(domain.getResourceId())
                .name(domain.getName())
                .fileId(domain.getFileId())
                .build();
    }

    public TemplateAttachmentEntity map(TemplateAttachment domain) {
        return TemplateAttachmentEntity.builder()
                .attachmentId(domain.getAttachmentId())
                .name(domain.getName())
                .fileId(domain.getFileId())
                .build();
    }
}
