package pl.app.mail.template.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.util.EntityLocationUriUtils;
import pl.app.mail.template.application.domain.model.Template;
import pl.app.mail.template.application.pornt.in.CreateTemplateCommand;
import pl.app.mail.template.application.pornt.in.CreateTemplateUseCase;
import pl.app.mail.template.application.pornt.in.DeleteTemplateUseCase;

@RestController
@RequestMapping(TemplateController.resourcePath)
@RequiredArgsConstructor
class TemplateController {
    public static final String resourceName = "templates";
    public static final String resourcePath = "/api/v1/" + resourceName;
    public final CreateTemplateUseCase createTemplate;
    public final DeleteTemplateUseCase deleteTemplate;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody CreateTemplateCommand command, HttpServletRequest request) {
        Template template = createTemplate.create(command);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(template.getTemplateId(), request.getRequestURI()))
                .body(template.getTemplateId());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        deleteTemplate.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
