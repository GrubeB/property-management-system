package pl.app.common.core.service;

import jakarta.validation.Valid;
import org.springframework.data.domain.Persistable;
import org.springframework.lang.NonNull;

public interface CommandService {
    interface Creatable<ID, ENTITY extends Persistable<ID>> {
        ENTITY create(@Valid ENTITY entity);
    }
    interface CreatableWithParent<ID, ENTITY extends Persistable<ID>> {
        ENTITY create(@NonNull ID parentId, @Valid ENTITY entity);
    }
    interface Updatable<ID, ENTITY extends Persistable<ID>> {
        ENTITY update(@NonNull ID id, ENTITY entity);
    }
    interface DeletableById<ID, ENTITY extends Persistable<ID>> {
        void deleteById(@NonNull ID id);
    }
}
