package pl.app.mail.template.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Template {
    private String templateId;
    private String name;
    private String applicationId;
    private String templateFileId;

    private Set<TemplateProperty> properties = new LinkedHashSet<>();
    private Set<TemplateResource> resources = new LinkedHashSet<>();
    private Set<TemplateAttachment> attachments = new LinkedHashSet<>();

    public Template() {
        this.templateId = UUID.randomUUID().toString();
    }

    public Template(String name, String applicationId) {
        this();
        this.name = name;
        this.applicationId = applicationId;
    }

    public void setTemplateFile(String fileId) {
        this.templateFileId = fileId;
    }

    public boolean addProperty(String name, String defaultValue) {
        if (this.properties.stream().anyMatch(p -> p.getName().equals(name))) {
            return false;
        }
        TemplateProperty newTemplateProperty = new TemplateProperty(name, defaultValue);
        return this.properties.add(newTemplateProperty);
    }

    public boolean removePropertyByName(String name) {
        return properties.removeIf(property -> property.getName().equals(name));
    }

    public boolean addResource(String resourceName, String fileId) {
        if (this.resources.stream().anyMatch(r -> r.getName().equals(resourceName))) {
            return false;
        }
        TemplateResource templateResource = new TemplateResource(resourceName, fileId);
        return this.resources.add(templateResource);
    }

    public boolean removeResourceByName(String name) {
        return this.resources.removeIf(resource -> resource.getName().equals(name));
    }

    public boolean addAttachment(String attachmentName, String fileId) {
        if (this.attachments.stream().anyMatch(a -> a.getName().equals(attachmentName))) {
            return false;
        }
        TemplateAttachment templateAttachment = new TemplateAttachment(attachmentName, fileId);
        return this.attachments.add(templateAttachment);
    }

    public boolean removeAttachmentByName(String name) {
        return this.attachments.removeIf(resource -> resource.getName().equals(name));
    }
}
