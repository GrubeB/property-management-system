package pl.app.mail.template.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.mail.template.adapter.out.persistence.model.TemplateEntity;
import pl.app.mail.template.adapter.query.TemplateQueryService;

import java.util.List;

@RestController
@RequestMapping(TemplateQueryController.resourcePath)
@RequiredArgsConstructor
class TemplateQueryController {
    public static final String resourceName = "templates";
    public static final String resourcePath = "/api/v1/" + resourceName;
    private final TemplateQueryService service;

    @GetMapping
    public ResponseEntity<List<TemplateEntity>> fetchAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.fetchAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TemplateEntity> fetchById(@PathVariable String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.fetchById(id));
    }
}
