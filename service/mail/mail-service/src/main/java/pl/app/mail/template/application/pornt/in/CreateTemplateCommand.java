package pl.app.mail.template.application.pornt.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTemplateCommand implements Serializable {
    private String name;
    private String applicationId;
    private String templateFileName;
    private String templateContext;
    private Set<CreatePropertyDto> properties;
    private Set<CreateResourceDto> resources;
    private Set<CreateAttachmentDto> attachments;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreatePropertyDto implements Serializable {
        private String name;
        private String defaultValue;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateResourceDto implements Serializable {
        private String name;
        private String context;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateAttachmentDto implements Serializable {
        private String name;
        private String context;
    }

}
