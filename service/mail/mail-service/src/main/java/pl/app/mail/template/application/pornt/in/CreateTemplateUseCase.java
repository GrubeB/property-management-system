package pl.app.mail.template.application.pornt.in;

import pl.app.mail.template.application.domain.model.Template;

public interface CreateTemplateUseCase {
    Template create(CreateTemplateCommand command);
}
