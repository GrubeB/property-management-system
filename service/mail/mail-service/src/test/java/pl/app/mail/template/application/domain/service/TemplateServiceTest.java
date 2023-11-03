package pl.app.mail.template.application.domain.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import pl.app.file.file.command.CreateFileFromBase64Command;
import pl.app.file.file.model.File;
import pl.app.file.file.service.FileService;
import pl.app.mail.template.application.domain.model.Template;
import pl.app.mail.template.application.domain.model.TemplateAttachment;
import pl.app.mail.template.application.domain.model.TemplateProperty;
import pl.app.mail.template.application.domain.model.TemplateResource;
import pl.app.mail.template.application.pornt.in.CreateTemplateCommand;
import pl.app.mail.template.application.pornt.out.LoadTemplatePort;
import pl.app.mail.template.application.pornt.out.RemoveTemplatePort;
import pl.app.mail.template.application.pornt.out.SaveTemplatePort;

import java.nio.charset.StandardCharsets;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TemplateServiceTest {

    @InjectMocks
    private TemplateService templateService;

    @Mock
    private LoadTemplatePort loadTemplatePort;
    @Mock
    private RemoveTemplatePort removeTemplatePort;
    @Mock
    private SaveTemplatePort saveTemplatePort;

    @Mock
    private FileService fileService;

    @Test
    void whenValidCommand_thenShouldCreateTemplate() {
        // given
        when(fileService.createFile(any(CreateFileFromBase64Command.class))).thenReturn(getExampleFile());
        when(saveTemplatePort.save(any())).thenAnswer((Answer<Template>) invocation -> invocation.getArgument(0));
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

    private Template getExampleTemplate() {
        return new Template(
                "1L",
                "name",
                "applicationId",
                "fileId-1L",
                Set.of(new TemplateProperty("propertyName", "propertyDefaultValuer")),
                Set.of(new TemplateResource("resourceName", "fileId-2L")),
                Set.of(new TemplateAttachment("resourceName", "fileId-3L"))
        );
    }

    private File getExampleFile() {
        return File.builder()
                .id("fileId-1L")
                .fileName("fileName")
                .contentType("type")
                .size(1000)
                .storageDirectoryName("storageDirectoryName")
                .storageFileName("storageFileName")
                .build();
    }
}