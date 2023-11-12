package pl.app.common.core.service;

import jakarta.validation.Valid;
import org.springframework.data.domain.Persistable;
import org.springframework.lang.NonNull;

public interface BaseCommandService {
    interface Creatable {
        interface SimpleCreatable<ID, ENTITY extends Persistable<ID>> {
            ENTITY create(@Valid ENTITY entity);
        }

        interface CreatableWithParent<ID, ENTITY extends Persistable<ID>, PARENT_ID> {
            ENTITY create(@NonNull PARENT_ID parentId, @Valid ENTITY entity);
        }

        interface DtoCreatable<ID, ENTITY extends Persistable<ID>, CREATE_DTO, DTO> {
            DTO createFromDto(@Valid CREATE_DTO dto);
        }

        interface DtoCreatableWithParent<ID, ENTITY extends Persistable<ID>, CREATE_DTO, DTO, PARENT_ID> {
            DTO createFromDto(@NonNull PARENT_ID parentId, @Valid CREATE_DTO dto);
        }
    }

    interface Updatable {
        interface SimpleUpdatable<ID, ENTITY extends Persistable<ID>> {
            ENTITY update(@NonNull ID id, ENTITY entity);
        }

        interface UpdatableWithParent<ID, ENTITY extends Persistable<ID>, PARENT_ID> {
            ENTITY update(PARENT_ID parentId, @NonNull ID id, ENTITY entity);
        }

        interface DtoUpdatable<ID, ENTITY extends Persistable<ID>, UPDATE_DTO, DTO> {
            DTO updateFromDto(@NonNull ID id, UPDATE_DTO dto);
        }

        interface DtoUpdatableWithParent<ID, ENTITY extends Persistable<ID>, UPDATE_DTO, DTO, PARENT_ID> {
            DTO updateFromDto(PARENT_ID parentId, @NonNull ID id, UPDATE_DTO dto);
        }
    }

    interface Deletable {
        interface SimpleDeletable<ID, ENTITY extends Persistable<ID>> {
            void deleteById(@NonNull ID id);
        }
    }

}
