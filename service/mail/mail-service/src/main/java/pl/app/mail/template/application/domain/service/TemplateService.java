package pl.app.mail.template.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.file.file.command.CreateFileFromBase64Command;
import pl.app.file.file.model.File;
import pl.app.file.file.service.FileService;
import pl.app.mail.template.application.domain.model.Template;
import pl.app.mail.template.application.pornt.in.CreateTemplateCommand;
import pl.app.mail.template.application.pornt.in.CreateTemplateUseCase;
import pl.app.mail.template.application.pornt.in.DeleteTemplateUseCase;
import pl.app.mail.template.application.pornt.out.LoadTemplatePort;
import pl.app.mail.template.application.pornt.out.RemoveTemplatePort;
import pl.app.mail.template.application.pornt.out.SaveTemplatePort;

@Service
@RequiredArgsConstructor
@Transactional
class TemplateService implements CreateTemplateUseCase, DeleteTemplateUseCase {
    @Value("${spring.application.name}")
    private String mailServiceApplicationId;

    private final FileService fileService;

    private final LoadTemplatePort loadTemplatePort;
    private final RemoveTemplatePort removeTemplatePort;
    private final SaveTemplatePort saveTemplatePort;

    @Override
    public Template create(CreateTemplateCommand command) {
        Template template = new Template(command.getName(), command.getApplicationId());

        File templateFile = fileService.createFile(new CreateFileFromBase64Command(command.getTemplateContext(), command.getTemplateFileName(), mailServiceApplicationId));
        template.setTemplateFile(templateFile.getId());

        command.getProperties().forEach(property -> template.addProperty(property.getName(), property.getDefaultValue()));

        command.getResources().stream()
                .map(resourceFile -> new CreateFileFromBase64Command(resourceFile.getContext(), resourceFile.getName(), mailServiceApplicationId))
                .forEach(createFileCommand -> {
                    File file = fileService.createFile(createFileCommand);
                    template.addResource(file.getFileName(), file.getId());
                });

        command.getAttachments().stream()
                .map(attachmentFile -> new CreateFileFromBase64Command(attachmentFile.getContext(), attachmentFile.getName(), mailServiceApplicationId))
                .forEach(createFileCommand -> {
                    File file = fileService.createFile(createFileCommand);
                    template.addAttachment(file.getFileName(), file.getId());
                });
        saveTemplatePort.save(template);
        return template;
    }

    @Override
    public void delete(String templateId) {
        removeTemplatePort.remove(templateId);
    }

}
