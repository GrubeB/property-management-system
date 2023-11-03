package pl.app.mail.template.adapter.query;

import pl.app.mail.template.adapter.out.persistence.model.TemplateEntity;

import java.util.List;


public interface TemplateQueryService {
    TemplateEntity fetchById(String templateId);

    List<TemplateEntity> fetchAll();

    TemplateEntity fetchByNameAndApplicationId(String templateName, String applicationId);
}
