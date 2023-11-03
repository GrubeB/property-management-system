package pl.app.common.core.service;

import jakarta.validation.Valid;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import pl.app.common.core.service.exception.NotFoundException;

public interface SimpleCommandService {

    @Validated
    interface Creatable<ID, ENTITY extends Persistable<ID>> extends
            CommandService.Creatable<ID, ENTITY> {
        @Override
        default ENTITY create(@Valid ENTITY entity) {
            beforeSave(entity);
            ENTITY savedEntity = getRepository().save(entity);
            afterSave(savedEntity);
            return savedEntity;
        }

        default void beforeSave(ENTITY entity) {
        }

        default void afterSave(ENTITY savedEntity) {
        }

        JpaRepository<ENTITY, ID> getRepository();
    }

    interface Updatable<ID, ENTITY extends Persistable<ID>> extends
            CommandService.Updatable<ID, ENTITY> {
        @Override
        default ENTITY update(@NonNull ID id, ENTITY newEntity) {
            ENTITY existingEntity = getRepository().findById(id)
                    .orElseThrow(() -> new NotFoundException("Not found object with id: " + id));
            beforeUpdate(id, existingEntity, newEntity);
            merge(existingEntity, newEntity);
            ENTITY savedEntity = getRepository().save(newEntity);
            afterUpdate(id, savedEntity, existingEntity);
            return savedEntity;
        }

        default void beforeUpdate(ID id, ENTITY existingEntity, ENTITY newEntity) {
        }

        default void merge(ENTITY existingEntity, ENTITY newEntity) {

        }

        default void afterUpdate(ID id, ENTITY savedEntity, ENTITY oldEntity) {
        }

        JpaRepository<ENTITY, ID> getRepository();
    }

    interface DeletableById<ID, ENTITY extends Persistable<ID>> extends
            CommandService.DeletableById<ID, ENTITY> {
        @Override
        default void deleteById(@NonNull ID id) {
            ENTITY existingEntity = getRepository().findById(id)
                    .orElseThrow(() -> new NotFoundException("Not found object with id: " + id));
            beforeDelete(id, existingEntity);
            getRepository().deleteById(id);
            afterDelete(id);
        }

        default void beforeDelete(ID id, ENTITY existingEntity) {
        }

        default void afterDelete(ID id) {
        }

        JpaRepository<ENTITY, ID> getRepository();
    }

    @Validated
    interface CreatableWithParent<ID, ENTITY extends Persistable<ID>> extends
            CommandService.CreatableWithParent<ID, ENTITY>,
            Creatable<ID, ENTITY> {

        @Override
        default ENTITY create(@NonNull ID parentId, @Valid ENTITY entity) {
            settingParentBeforeSave(parentId, entity);
            return create(entity);
        }

        default void settingParentBeforeSave(ID parentId, ENTITY entity) {
        }
    }
}
