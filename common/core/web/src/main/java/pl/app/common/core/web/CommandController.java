package pl.app.common.core.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Persistable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.core.service.BaseCommandService;
import pl.app.common.core.service.path_variables.PathVariables;
import pl.app.common.util.EntityLocationUriUtils;

public interface CommandController {
    interface Creatable {
        interface SimpleCreatable<ID, ENTITY extends Persistable<ID>> {
            @PostMapping
            default ResponseEntity<ENTITY> create(@RequestBody ENTITY entity, HttpServletRequest request) {
                ENTITY saved = getService().create(entity);
                return ResponseEntity
                        .created(EntityLocationUriUtils.createdEntityLocationURI(saved.getId(), request.getRequestURI()))
                        .body(saved);
            }

            BaseCommandService.Creatable.SimpleCreatable<ID, ENTITY> getService();

        }

        interface CreatableWithParent<ID, ENTITY extends Persistable<ID>, PARENT_ID> extends
                ControllerSupport.ParentIdResolverSupport<PARENT_ID> {
            @PostMapping
            default ResponseEntity<ENTITY> create(PathVariables pathVariables, @RequestBody ENTITY entity, HttpServletRequest request) {
                ENTITY saved = getService().create(getParentId(pathVariables), entity);
                return ResponseEntity
                        .created(EntityLocationUriUtils.createdEntityLocationURI(saved.getId(), request.getRequestURI()))
                        .body(saved);
            }

            BaseCommandService.Creatable.CreatableWithParent<ID, ENTITY, PARENT_ID> getService();
        }

        interface DtoCreatable<ID, ENTITY extends Persistable<ID>, CREATE_DTO, DTO> {

            @PostMapping
            default ResponseEntity<DTO> createFromDto(@RequestBody CREATE_DTO createDto) {
                DTO dto = getService().createFromDto(createDto);
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(dto);
            }

            BaseCommandService.Creatable.DtoCreatable<ID, ENTITY, CREATE_DTO, DTO> getService();
        }

        interface DtoCreatableWithParent<ID, ENTITY extends Persistable<ID>, CREATE_DTO, DTO, PARENT_ID> extends
                ControllerSupport.ParentIdResolverSupport<PARENT_ID> {
            @PostMapping
            default ResponseEntity<DTO> createFromDto(PathVariables pathVariables, @RequestBody CREATE_DTO createDto) {
                DTO dto = getService().createFromDto(getParentId(pathVariables), createDto);
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(dto);
            }

            BaseCommandService.Creatable.DtoCreatableWithParent<ID, ENTITY, CREATE_DTO, DTO, PARENT_ID> getService();
        }
    }

    interface Updatable {
        interface SimpleUpdatable<ID, ENTITY extends Persistable<ID>> {
            @PutMapping(path = "/{id}")
            default ResponseEntity<ENTITY> update(@PathVariable ID id, @RequestBody ENTITY entity) {
                ENTITY updated = getService().update(id, entity);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(updated);
            }

            BaseCommandService.Updatable.SimpleUpdatable<ID, ENTITY> getService();
        }

        interface UpdatableWithParent<ID, ENTITY extends Persistable<ID>, PARENT_ID> extends
                ControllerSupport.ParentIdResolverSupport<PARENT_ID> {
            @PutMapping(path = "/{id}")
            default ResponseEntity<ENTITY> update(PathVariables pathVariables, @PathVariable ID id, @RequestBody ENTITY entity) {
                ENTITY updated = getService().update(getParentId(pathVariables), id, entity);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(updated);
            }

            BaseCommandService.Updatable.UpdatableWithParent<ID, ENTITY, PARENT_ID> getService();
        }

        interface DtoUpdatable<ID, ENTITY extends Persistable<ID>, UPDATE_DTO, DTO> {
            @PutMapping(path = "/{id}")
            default ResponseEntity<DTO> update(@PathVariable ID id, @RequestBody UPDATE_DTO updateDto) {
                DTO dto = getService().updateFromDto(id, updateDto);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(dto);
            }

            BaseCommandService.Updatable.DtoUpdatable<ID, ENTITY, UPDATE_DTO, DTO> getService();
        }

        interface DtoUpdatableWithParent<ID, ENTITY extends Persistable<ID>, UPDATE_DTO, DTO, PARENT_ID> extends
                ControllerSupport.ParentIdResolverSupport<PARENT_ID> {
            @PutMapping(path = "/{id}")
            default ResponseEntity<DTO> update(PathVariables pathVariables, @PathVariable ID id, @RequestBody UPDATE_DTO updateDto) {
                DTO dto = getService().updateFromDto(getParentId(pathVariables), id, updateDto);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(dto);
            }

            BaseCommandService.Updatable.DtoUpdatableWithParent<ID, ENTITY, UPDATE_DTO, DTO, PARENT_ID> getService();
        }
    }

    interface Deletable {

        interface SimpleDeletable<ID, ENTITY extends Persistable<ID>> {
            @DeleteMapping(path = "/{id}")
            default ResponseEntity<ENTITY> delete(@PathVariable ID id) {
                getService().deleteById(id);
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .build();
            }

            BaseCommandService.Deletable.SimpleDeletable<ID, ENTITY> getService();
        }

    }
}
