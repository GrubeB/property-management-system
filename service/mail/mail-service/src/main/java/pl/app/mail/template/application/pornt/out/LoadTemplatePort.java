package pl.app.mail.template.application.pornt.out;

import pl.app.mail.template.application.domain.model.Template;

public interface LoadTemplatePort {
    Template load(String templateId);
}
