package pl.app.common.core.service;

import jakarta.validation.Valid;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import pl.app.common.core.service.exception.NotFoundException;

public interface CommandService {
    interface Creatable {
        @Validated
        interface SimpleCreatable<ID, ENTITY extends Persistable<ID>> extends
                BaseCommandService.Creatable.SimpleCreatable<ID, ENTITY> {
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

        @Validated
        interface DtoCreatable<ID, ENTITY extends Persistable<ID>, CREATE_DTO, DTO> extends
                BaseCommandService.Creatable.DtoCreatable<ID, ENTITY, CREATE_DTO, DTO>,
                CommandService.Creatable.SimpleCreatable<ID, ENTITY>,
                ServiceSupport.InterfaceArgumentResolverSupport,
                ServiceSupport.MapperSupport {
            @Override
            default DTO createFromDto(@Valid CREATE_DTO dto) {
                ENTITY entity = getMapper().map(dto, resolveArgumentAtIndex(DtoCreatable.class, 1));
                beforeDtoSave(dto, entity);
                ENTITY savedEntity = create(entity);
                afterDtoSave(dto, savedEntity);
                return getMapper().map(savedEntity, resolveArgumentAtIndex(DtoCreatable.class, 3));
            }

            default void beforeDtoSave(CREATE_DTO dto, ENTITY entity) {
            }

            default void afterDtoSave(CREATE_DTO dto, ENTITY savedEntity) {
            }
        }

        @Validated
        interface CreatableWithParent<ID, ENTITY extends Persistable<ID>, PARENT_ID> extends
                BaseCommandService.Creatable.CreatableWithParent<ID, ENTITY, PARENT_ID>,
                CommandService.Creatable.SimpleCreatable<ID, ENTITY>,
                ServiceSupport.ParentSetterSupport<ENTITY, PARENT_ID> {
            @Override
            default ENTITY create(@NonNull PARENT_ID parentId, @Valid ENTITY entity) {
                settingParentBeforeSave(parentId, entity);
                return create(entity);
            }
        }

        @Validated
        interface DtoCreatableWithParent<ID, ENTITY extends Persistable<ID>, CREATE_DTO, DTO, PARENT_ID> extends
                BaseCommandService.Creatable.DtoCreatableWithParent<ID, ENTITY, CREATE_DTO, DTO, PARENT_ID>,
                CommandService.Creatable.SimpleCreatable<ID, ENTITY>,
                ServiceSupport.ParentSetterSupport<ENTITY, PARENT_ID>,
                ServiceSupport.InterfaceArgumentResolverSupport,
                ServiceSupport.MapperSupport {
            @Override
            default DTO createFromDto(@NonNull PARENT_ID parentId, @Valid CREATE_DTO dto) {
                ENTITY entity = getMapper().map(dto, resolveArgumentAtIndex(DtoCreatableWithParent.class, 1));
                settingParentBeforeSave(parentId, entity);
                beforeDtoSave(dto, entity);
                ENTITY savedEntity = create(entity);
                afterDtoSave(dto, savedEntity);
                return getMapper().map(savedEntity, resolveArgumentAtIndex(DtoCreatableWithParent.class, 3));
            }

            default void beforeDtoSave(CREATE_DTO dto, ENTITY entity) {
            }

            default void afterDtoSave(CREATE_DTO dto, ENTITY savedEntity) {
            }
        }

    }

    interface Updatable {
        interface SimpleUpdatable<ID, ENTITY extends Persistable<ID>> extends
                BaseCommandService.Updatable.SimpleUpdatable<ID, ENTITY> {
            @Override
            default ENTITY update(@NonNull ID id, ENTITY entity) {
                ENTITY existingEntity = getRepository().findById(id)
                        .orElseThrow(() -> new NotFoundException("Not found object with id: " + id));
                beforeUpdate(id, existingEntity, entity);
                ENTITY mergedEntity = merge(existingEntity, entity);
                ENTITY savedEntity = getRepository().save(mergedEntity);
                afterUpdate(id, savedEntity, existingEntity);
                return savedEntity;
            }

            default void beforeUpdate(ID id, ENTITY existingEntity, ENTITY newEntity) {
            }

            default ENTITY merge(ENTITY existingEntity, ENTITY newEntity) {
                return newEntity;
            }

            default void afterUpdate(ID id, ENTITY savedEntity, ENTITY oldEntity) {
            }

            JpaRepository<ENTITY, ID> getRepository();
        }


        interface UpdatableWithParent<ID, ENTITY extends Persistable<ID>, PARENT_ID> extends
                BaseCommandService.Updatable.UpdatableWithParent<ID, ENTITY, PARENT_ID>,
                ServiceSupport.ParentSetterSupport<ENTITY, PARENT_ID> {
            @Override
            default ENTITY update(PARENT_ID parentId, @NonNull ID id, ENTITY entity) {
                ENTITY existingEntity = getRepository().findById(id)
                        .orElseThrow(() -> new NotFoundException("Not found object with id: " + id));
                ENTITY mergedEntity = merge(existingEntity, entity);
                settingParentBeforeSave(parentId, mergedEntity);
                beforeUpdate(id, existingEntity, mergedEntity);
                ENTITY savedEntity = getRepository().save(mergedEntity);
                afterUpdate(id, savedEntity, existingEntity);
                return savedEntity;
            }


            default void beforeUpdate(ID id, ENTITY existingEntity, ENTITY newEntity) {
            }

            default ENTITY merge(ENTITY existingEntity, ENTITY newEntity) {
                return newEntity;
            }

            default void afterUpdate(ID id, ENTITY savedEntity, ENTITY oldEntity) {
            }

            JpaRepository<ENTITY, ID> getRepository();
        }

        interface DtoUpdatable<ID, ENTITY extends Persistable<ID>, UPDATE_DTO, DTO> extends
                BaseCommandService.Updatable.DtoUpdatable<ID, ENTITY, UPDATE_DTO, DTO>,
                ServiceSupport.InterfaceArgumentResolverSupport,
                ServiceSupport.MapperSupport {
            @Override
            default DTO updateFromDto(@NonNull ID id, UPDATE_DTO dto) {
                ENTITY existingEntity = getRepository().findById(id)
                        .orElseThrow(() -> new NotFoundException("Not found object with id: " + id));
                ENTITY entityFromDto = getMapper().map(dto, resolveArgumentAtIndex(Creatable.DtoCreatableWithParent.class, 1));
                ENTITY mergedEntity = merge(existingEntity, entityFromDto);
                beforeDtoUpdate(id, dto, existingEntity, mergedEntity);
                ENTITY savedEntity = getRepository().save(mergedEntity);
                afterDtoUpdate(id, dto, savedEntity);
                return getMapper().map(savedEntity, resolveArgumentAtIndex(Creatable.DtoCreatableWithParent.class, 3));
            }


            default void beforeDtoUpdate(ID id, UPDATE_DTO dto, ENTITY existingEntity, ENTITY newEntity) {
            }

            default ENTITY merge(ENTITY existingEntity, ENTITY newEntity) {
                return newEntity;
            }

            default void afterDtoUpdate(ID id, UPDATE_DTO dto, ENTITY savedEntity) {
            }

            JpaRepository<ENTITY, ID> getRepository();
        }

        interface DtoUpdatableWithParent<ID, ENTITY extends Persistable<ID>, UPDATE_DTO, DTO, PARENT_ID> extends
                BaseCommandService.Updatable.DtoUpdatableWithParent<ID, ENTITY, UPDATE_DTO, DTO, PARENT_ID>,
                ServiceSupport.ParentSetterSupport<ENTITY, PARENT_ID>,
                ServiceSupport.InterfaceArgumentResolverSupport,
                ServiceSupport.MapperSupport {
            @Override
            default DTO updateFromDto(PARENT_ID parentId, @NonNull ID id, UPDATE_DTO dto) {
                ENTITY existingEntity = getRepository().findById(id)
                        .orElseThrow(() -> new NotFoundException("Not found object with id: " + id));
                ENTITY entityFromDto = getMapper().map(dto, resolveArgumentAtIndex(Creatable.DtoCreatableWithParent.class, 1));
                ENTITY mergedEntity = merge(existingEntity, entityFromDto);
                settingParentBeforeSave(parentId, mergedEntity);
                beforeDtoUpdate(id, dto, existingEntity, mergedEntity);
                ENTITY savedEntity = getRepository().save(mergedEntity);
                afterDtoUpdate(id, dto, savedEntity);
                return getMapper().map(savedEntity, resolveArgumentAtIndex(Creatable.DtoCreatableWithParent.class, 3));
            }

            default void beforeDtoUpdate(ID id, UPDATE_DTO dto, ENTITY existingEntity, ENTITY newEntity) {
            }

            default ENTITY merge(ENTITY existingEntity, ENTITY newEntity) {
                return newEntity;
            }

            default void afterDtoUpdate(ID id, UPDATE_DTO dto, ENTITY savedEntity) {
            }

            JpaRepository<ENTITY, ID> getRepository();
        }
    }

    interface Deletable {
        interface SimpleDeletable<ID, ENTITY extends Persistable<ID>> extends
                BaseCommandService.Deletable.SimpleDeletable<ID, ENTITY> {
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
    }
}
