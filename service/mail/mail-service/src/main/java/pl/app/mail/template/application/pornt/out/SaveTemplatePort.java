package pl.app.mail.template.application.pornt.out;

import pl.app.mail.template.application.domain.model.Template;

public interface SaveTemplatePort {
    Template save(Template template);
}
