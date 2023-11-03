package pl.app.common.core.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Persistable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.core.service.CommandService;
import pl.app.common.core.service.path_variables.PathVariables;
import pl.app.common.util.EntityLocationUriUtils;

import java.util.List;
import java.util.UUID;

public interface CommandController {

    interface Creatable<ID, ENTITY extends Persistable<ID>> {
        @PostMapping
        default ResponseEntity<ENTITY> create(@RequestBody ENTITY entity, HttpServletRequest request) {
            ENTITY saved = getService().create(entity);
            return ResponseEntity
                    .created(EntityLocationUriUtils.createdEntityLocationURI(saved.getId(), request.getRequestURI()))
                    .body(saved);
        }

        CommandService.Creatable<ID, ENTITY> getService();
    }

    interface CreatableWithParent<ID, ENTITY extends Persistable<ID>> {
        @PostMapping
        default ResponseEntity<ENTITY> create(PathVariables pathVariables, @RequestBody ENTITY entity, HttpServletRequest request) {
            ENTITY saved = getService().create(getParentId(pathVariables), entity);
            return ResponseEntity
                    .created(EntityLocationUriUtils.createdEntityLocationURI(saved.getId(), request.getRequestURI()))
                    .body(saved);
        }

        @SuppressWarnings("unchecked")
        default ID getParentId(PathVariables pathVariables) {
            try {
                if (getParentIdPathVariableNames() != null) {
                    String parentIdPathVariableName = getParentIdPathVariableNames().get(0);
                    return (ID) UUID.fromString(pathVariables.get(parentIdPathVariableName));
                }
                throw new RuntimeException("parent id could not be extracted from path");
            } catch (Exception e) {
                throw new RuntimeException("parent id could not be extracted from path");
            }
        }

        CommandService.CreatableWithParent<ID, ENTITY> getService();

        List<String> getParentIdPathVariableNames();
    }

    interface Updatable<ID, ENTITY extends Persistable<ID>> {
        @PutMapping(path = "/{id}")
        default ResponseEntity<ENTITY> create(@PathVariable ID id, @RequestBody ENTITY entity) {
            ENTITY updated = getService().update(id, entity);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(updated);
        }

        CommandService.Updatable<ID, ENTITY> getService();
    }

    interface DeletableById<ID, ENTITY extends Persistable<ID>> {
        @DeleteMapping(path = "/{id}")
        default ResponseEntity<ENTITY> create(@PathVariable ID id) {
            getService().deleteById(id);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        }

        CommandService.DeletableById<ID, ENTITY> getService();
    }
}
