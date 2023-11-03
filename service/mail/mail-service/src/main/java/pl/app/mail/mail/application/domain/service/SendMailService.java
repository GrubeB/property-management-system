package pl.app.mail.mail.application.domain.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import pl.app.file.file.service.FileService;
import pl.app.mail.mail.application.port.in.SendMailCommand;
import pl.app.mail.mail.application.port.in.SendMailUseCase;
import pl.app.mail.template.adapter.out.persistence.model.TemplateAttachmentEntity;
import pl.app.mail.template.adapter.out.persistence.model.TemplateEntity;
import pl.app.mail.template.adapter.out.persistence.model.TemplatePropertyEntity;
import pl.app.mail.template.adapter.out.persistence.model.TemplateResourceEntity;
import pl.app.mail.template.adapter.query.TemplateQueryService;

import java.nio.charset.StandardCharsets;
import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
class SendMailService implements SendMailUseCase {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine thymeleafTemplateEngine;
    private final TemplateQueryService templateService;
    private final FileService fileService;

    @Override
    public void sendMail(final String to, final String subject, final String template, final Map<String, Object> properties,
                         final Map<String, Resource> resourceMap, final Map<String, Resource> attachmentMap) {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(properties);
        String htmlBody = thymeleafTemplateEngine.process(template, thymeleafContext);
        MimeMessage mimeMessage = getMimeMessage(to, subject, htmlBody, resourceMap, attachmentMap);
        mailSender.send(mimeMessage);
        log.info("Email has been send to {}", to);
    }

    @Override
    public void sendMail(SendMailCommand command) {
        TemplateEntity template = templateService.fetchByNameAndApplicationId(command.getTemplateName(), command.getApplicationId());
        final Map<String, Object> propertyMap = getProperties(command.getProperties(), template.getProperties());
        final Map<String, Resource> resourceMap = getResourceMap(template.getResources());
        final Map<String, Resource> attachmentMap = getAttachmentMap(template.getAttachments());
        final String templateHtml = getTemplate(template.getTemplateFileId());
        sendMail(command.getTo(), command.getSubject(), templateHtml, propertyMap, resourceMap, attachmentMap);
    }

    private MimeMessage getMimeMessage(final String to, final String subject, final String text,
                                       final Map<String, Resource> resourceMap, final Map<String, Resource> attachmentMap) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(text, true);
            Optional.ofNullable(resourceMap).ifPresent(resources -> resources.forEach((k, v) -> addResource(mimeMessageHelper, k, v)));
            Optional.ofNullable(attachmentMap).ifPresent(attachments -> attachments.forEach((k, v) -> addAttachment(mimeMessageHelper, k, v)));
            return mimeMessage;
        } catch (MessagingException exception) {
            log.error("Email has not been send to {}, because an error occurred while creating email", to);
            throw new RuntimeException(exception.getMessage());
        }
    }

    private static void addAttachment(MimeMessageHelper mimeMessageHelper, String attachmentName, Resource resource) {
        try {
            mimeMessageHelper.addAttachment(attachmentName, resource);
        } catch (MessagingException e) {
            log.error("Attachment has not been added to the email, because an error occurred. Attachment name:" + attachmentName);
        }
    }

    private static void addResource(MimeMessageHelper mimeMessageHelper, String resourceName, Resource resource) {
        try {
            mimeMessageHelper.addInline(resourceName, resource);
        } catch (MessagingException e) {
            log.error("Resource has not been added to the email, because an error occurred. Resource name:" + resourceName);
        }
    }

    private Map<String, Resource> getAttachmentMap(Set<TemplateAttachmentEntity> attachments) {
        Map<String, Resource> attachmentMap = new HashMap<>();
        attachments.stream()
                .map(templateAttachments -> fileService.fetchFileAsResourceById(templateAttachments.getFileId()))
                .forEach(resource -> attachmentMap.put(resource.getFilename(), resource));
        return attachmentMap;
    }

    private Map<String, Resource> getResourceMap(Set<TemplateResourceEntity> resources) {
        Map<String, Resource> resourceMap = new HashMap<>();
        resources.stream()
                .map(templateResourceEntity -> fileService.fetchFileAsResourceById(templateResourceEntity.getFileId()))
                .forEach(resource -> resourceMap.put(resource.getFilename(), resource));
        return resourceMap;
    }

    private Map<String, Object> getProperties(List<SendMailCommand.Property> dtoProperties, Set<TemplatePropertyEntity> templateProperties) {
        Map<String, Object> propertyMap = new HashMap<>();
        // put template properties with default values
        templateProperties.forEach(propertyDto -> propertyMap.put(propertyDto.getName(), propertyDto.getDefaultValue()));
        // put properties from dto, if property is in the map then it will be replaced
        dtoProperties.forEach(propertyDto -> propertyMap.put(propertyDto.getName(), propertyDto.getValue()));
        return propertyMap;
    }

    private String getTemplate(String templateFileId) {
        byte[] decode = fileService.fetchFileContentById(templateFileId);
        return new String(decode, StandardCharsets.UTF_8);
    }
}
