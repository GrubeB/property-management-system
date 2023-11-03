package pl.app.mail.template.adapter.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.app.mail.template.adapter.out.persistence.model.TemplateEntity;
import pl.app.mail.template.adapter.out.persistence.repository.TemplateRepository;
import pl.app.mail.template.application.domain.exception.TemplateException;

import java.util.List;

@Service
@RequiredArgsConstructor
class TemplateQueryServiceImpl implements TemplateQueryService {
    private final TemplateRepository templateRepository;

    @Override
    public TemplateEntity fetchById(String templateId) {
        return templateRepository.findById(templateId)
                .orElseThrow(() -> TemplateException.NotFoundTemplateException.fromId(templateId));
    }

    @Override
    public List<TemplateEntity> fetchAll() {
        return templateRepository.findAll();
    }

    @Override
    public TemplateEntity fetchByNameAndApplicationId(String templateName, String applicationId) {
        return templateRepository.findByNameIgnoreCaseAndApplicationId(templateName, applicationId)
                .orElseThrow(() -> new TemplateException.NotFoundTemplateException("Not found template with name: " + templateName + " and applicationId: " + applicationId));
    }
}
