package pl.app.mail.template.application.domain.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.app.mail.template.AbstractClass;
import pl.app.mail.template.application.domain.model.Template;
import pl.app.mail.template.application.pornt.in.CreateTemplateCommand;
import pl.app.mail.template.application.pornt.out.LoadTemplatePort;

import java.nio.charset.StandardCharsets;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class TemplateServiceIntegrationTest extends AbstractClass {

    @Autowired
    private TemplateService templateService;
    private LoadTemplatePort loadTemplatePort;

    @Test
    void whenValidCommand_thenShouldCreateTemplate() {
        // given
        CreateTemplateCommand command = getExampleCreateTemplateCommand();
        // when
        Template template = templateService.create(command);
        // then
        assertThat(template.getTemplateId()).isNotNull();
        assertThat(template.getName()).isEqualTo("name");
        assertThat(template.getApplicationId()).isEqualTo("applicationId");
        assertThat(template.getTemplateFileId()).isNotNull();
        assertThat(template.getProperties()).hasSize(1);
        assertThat(template.getAttachments()).hasSize(1);
        assertThat(template.getResources()).hasSize(1);
    }

    @Test
    void whenDeletingExistingTemplate_thenTemplateShouldNotExist() {
        // given
        CreateTemplateCommand command = getExampleCreateTemplateCommand();
        Template template = templateService.create(command);
        // when
        templateService.delete(template.getTemplateId());
        // then
        assertThatThrownBy(() -> loadTemplatePort.load(template.getTemplateId())).isInstanceOf(RuntimeException.class);
    }

    private CreateTemplateCommand getExampleCreateTemplateCommand() {
        return new CreateTemplateCommand("name",
                "applicationId",
                "templateFileName",
                Base64.encodeBase64String("<h1>aa</h1>".getBytes(StandardCharsets.UTF_8)),
                Set.of(new CreateTemplateCommand.CreatePropertyDto("propertyName", "propertyDefaultValuer")),
                Set.of(new CreateTemplateCommand.CreateResourceDto("resourceName", Base64.encodeBase64String("<h1>aa</h1>".getBytes(StandardCharsets.UTF_8)))),
                Set.of(new CreateTemplateCommand.CreateAttachmentDto("attachmentName", Base64.encodeBase64String("<h1>aa</h1>".getBytes(StandardCharsets.UTF_8))))
        );
    }
}