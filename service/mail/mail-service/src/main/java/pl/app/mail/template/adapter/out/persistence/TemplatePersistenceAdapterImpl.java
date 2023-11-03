package pl.app.mail.template.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.app.mail.template.adapter.out.persistence.maper.TemplateMapper;
import pl.app.mail.template.adapter.out.persistence.model.TemplateEntity;
import pl.app.mail.template.adapter.out.persistence.repository.TemplateRepository;
import pl.app.mail.template.adapter.query.TemplateQueryService;
import pl.app.mail.template.application.domain.model.Template;
import pl.app.mail.template.application.pornt.out.LoadTemplatePort;
import pl.app.mail.template.application.pornt.out.RemoveTemplatePort;
import pl.app.mail.template.application.pornt.out.SaveTemplatePort;

@Service
@RequiredArgsConstructor
class TemplatePersistenceAdapterImpl implements
        LoadTemplatePort,
        RemoveTemplatePort,
        SaveTemplatePort {
    private final TemplateQueryService templateQueryService;
    private final TemplateRepository templateRepository;
    private final TemplateMapper templateMapper;

    @Override
    public Template load(String templateId) {
        TemplateEntity templateEntity = templateQueryService.fetchById(templateId);
        return templateMapper.map(templateEntity);
    }

    @Override
    public void remove(String templateId) {
        templateRepository.deleteById(templateId);
    }

    @Override
    public Template save(Template template) {
        TemplateEntity templateEntity = templateMapper.map(template);
        TemplateEntity savedTemplateEntity = templateRepository.save(templateEntity);
        return templateMapper.map(savedTemplateEntity);
    }
}
