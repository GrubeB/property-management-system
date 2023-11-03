package pl.app.common.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import pl.app.common.core.service.criteria.SearchCriteria;
import pl.app.common.core.service.criteria.SearchCriteriaSpecification;
import pl.app.common.core.service.dto.Dto;
import pl.app.common.core.service.exception.NotFoundException;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface QueryService {
    interface FullFetchable<ID, ENTITY> extends
            Fetchable<ID, ENTITY>,
            ClassFetchable<ID, ENTITY>,
            DtoFetchable<ID, ENTITY> {
    }

    interface Fetchable<ID, ENTITY> extends
            FetchableAll<ID, ENTITY>,
            FetchableByPageable<ID, ENTITY>,
            FetchableByCriteria<ID, ENTITY>,
            FetchableBySearchCriteria<ID, ENTITY>,
            FetchableById<ID, ENTITY> {
    }

    interface FetchableAll<ID, ENTITY> {
        default List<ENTITY> fetchAll() {
            return getRepository().findAll();
        }

        JpaRepository<ENTITY, ID> getRepository();
    }

    interface FetchableByPageable<ID, ENTITY> {
        default Page<ENTITY> fetchByPageable(Pageable pageable) {
            return getRepository().findAll(pageable);
        }

        JpaRepository<ENTITY, ID> getRepository();
    }

    interface FetchableByCriteria<ID, ENTITY> {
        default List<ENTITY> fetchByCriteria(Specification<ENTITY> specification) {
            return getSpecificationRepository().findAll(specification);
        }

        default Page<ENTITY> fetchByCriteria(Specification<ENTITY> specification, Pageable pageable) {
            return getSpecificationRepository().findAll(specification, pageable);
        }

        JpaSpecificationExecutor<ENTITY> getSpecificationRepository();
    }

    interface FetchableBySearchCriteria<ID, ENTITY> {
        default List<ENTITY> fetchByCriteria(SearchCriteria criteria) {
            Specification<ENTITY> specification = new SearchCriteriaSpecification<>(criteria);
            return getSpecificationRepository().findAll(specification);
        }

        default Page<ENTITY> fetchByCriteria(SearchCriteria criteria, Pageable pageable) {
            Specification<ENTITY> specification = new SearchCriteriaSpecification<>(criteria);
            return getSpecificationRepository().findAll(specification, pageable);
        }

        JpaSpecificationExecutor<ENTITY> getSpecificationRepository();
    }

    interface FetchableById<ID, ENTITY> {
        default ENTITY fetchById(@NonNull ID id) {
            return getRepository().findById(id)
                    .orElseThrow(() -> new NotFoundException("object not found with id: " + id));
        }

        JpaRepository<ENTITY, ID> getRepository();
    }

    interface ClassFetchable<ID, ENTITY> extends
            ClassFetchableAll<ID, ENTITY>,
            ClassFetchableByPageable<ID, ENTITY>,
            ClassFetchableByCriteria<ID, ENTITY>,
            ClassFetchableBySearchCriteria<ID, ENTITY>,
            ClassFetchableById<ID, ENTITY> {
    }

    interface ClassFetchableAll<ID, ENTITY> extends FetchableAll<ID, ENTITY>,DtoMapperSupport {
        default <T> List<T> fetchAll(Class<T> dtoClass) {
            List<ENTITY> entities = getRepository().findAll();
            return entities.stream()
                    .map(entity -> this.map(entity, dtoClass))
                    .collect(Collectors.toList());
        }

        JpaRepository<ENTITY, ID> getRepository();
    }

    interface ClassFetchableByPageable<ID, ENTITY> extends FetchableByPageable<ID, ENTITY>, DtoMapperSupport {
        default <T> Page<T> fetchByPageable(Pageable pageable, Class<T> dtoClass) {
            Page<ENTITY> page = getRepository().findAll(pageable);
            return page.map(e -> this.map(e, dtoClass));
        }

        JpaRepository<ENTITY, ID> getRepository();

    }

    interface ClassFetchableByCriteria<ID, ENTITY> extends FetchableByCriteria<ID, ENTITY>, DtoMapperSupport {
        default <T> List<T> fetchByCriteria(Specification<ENTITY> specification, Class<T> dtoClass) {
            List<ENTITY> entities = getSpecificationRepository().findAll(specification);
            return entities.stream()
                    .map(e -> this.map(e, dtoClass))
                    .collect(Collectors.toList());
        }

        default <T> Page<T> fetchByCriteria(Specification<ENTITY> specification, Pageable pageable, Class<T> dtoClass) {
            Page<ENTITY> page = getSpecificationRepository().findAll(specification, pageable);
            return page.map(e -> this.map(e, dtoClass));
        }


        JpaSpecificationExecutor<ENTITY> getSpecificationRepository();
    }

    interface ClassFetchableBySearchCriteria<ID, ENTITY> extends FetchableBySearchCriteria<ID, ENTITY>, DtoMapperSupport {
        default <T> List<T> fetchByCriteria(SearchCriteria criteria, Class<T> dtoClass) {
            Specification<ENTITY> specification = new SearchCriteriaSpecification<>(criteria);
            List<ENTITY> entities = getSpecificationRepository().findAll(specification);
            return entities.stream()
                    .map(e -> this.map(e, dtoClass))
                    .collect(Collectors.toList());
        }

        default <T> Page<T> fetchByCriteria(SearchCriteria criteria, Pageable pageable, Class<T> dtoClass) {
            Specification<ENTITY> specification = new SearchCriteriaSpecification<>(criteria);
            Page<ENTITY> page = getSpecificationRepository().findAll(specification, pageable);
            return page.map(e -> this.map(e, dtoClass));
        }

        JpaSpecificationExecutor<ENTITY> getSpecificationRepository();
    }

    interface ClassFetchableById<ID, ENTITY> extends FetchableById<ID, ENTITY>, DtoMapperSupport {
        default <T> T fetchById(@NonNull ID id, Class<T> dtoClass) {
            ENTITY entity = getRepository().findById(id)
                    .orElseThrow(() -> new NotFoundException("object not found with id: " + id));
            return this.map(entity, dtoClass);
        }

        JpaRepository<ENTITY, ID> getRepository();
    }

    interface DtoFetchable<ID, ENTITY> extends
            DtoFetchableAll<ID, ENTITY>,
            DtoFetchableByPageable<ID, ENTITY>,
            DtoFetchableByCriteria<ID, ENTITY>,
            DtoFetchableBySearchCriteria<ID, ENTITY>,
            DtoFetchableById<ID, ENTITY> {
    }

    interface DtoFetchableAll<ID, ENTITY> extends ClassFetchableAll<ID, ENTITY>, DtoFetchableSupport {
        default <T> List<T> fetchAll(Dto dtoClass) {
            List<ENTITY> entities = getRepository().findAll();
            return entities.stream()
                    .map(entity -> {
                        T dto = this.map(entity, getClass(dtoClass));
                        return dto;
                    })
                    .collect(Collectors.toList());
        }

        JpaRepository<ENTITY, ID> getRepository();
    }

    interface DtoFetchableByPageable<ID, ENTITY> extends ClassFetchableByPageable<ID, ENTITY>, DtoFetchableSupport {
        default <T> Page<T> fetchByPageable(Pageable pageable, Dto dto) {
            Page<ENTITY> page = getRepository().findAll(pageable);
            return page.map(e -> this.map(e, getClass(dto)));
        }

        JpaRepository<ENTITY, ID> getRepository();
    }

    interface DtoFetchableByCriteria<ID, ENTITY> extends ClassFetchableByCriteria<ID, ENTITY>, DtoFetchableSupport {
        default <T> List<T> fetchByCriteria(Specification<ENTITY> specification, Dto dtoClass) {
            List<ENTITY> entities = getSpecificationRepository().findAll(specification);
            return entities.stream()
                    .map(entity -> {
                        T dto = this.map(entity, getClass(dtoClass));
                        return dto;
                    })
                    .collect(Collectors.toList());
        }

        default <T> Page<T> fetchByCriteria(Specification<ENTITY> specification, Pageable pageable, Dto dto) {
            Page<ENTITY> page = getSpecificationRepository().findAll(specification, pageable);
            return page.map(e -> this.map(e, getClass(dto)));
        }

        JpaSpecificationExecutor<ENTITY> getSpecificationRepository();
    }

    interface DtoFetchableBySearchCriteria<ID, ENTITY> extends ClassFetchableBySearchCriteria<ID, ENTITY>, DtoFetchableSupport {
        default <T> List<T> fetchByCriteria(SearchCriteria criteria, Dto dtoClass) {
            Specification<ENTITY> specification = new SearchCriteriaSpecification<>(criteria);
            List<ENTITY> entities = getSpecificationRepository().findAll(specification);
            return entities.stream()
                    .map(entity -> {
                        T dto = this.map(entity, getClass(dtoClass));
                        return dto;
                    })
                    .collect(Collectors.toList());
        }

        default <T> Page<T> fetchByCriteria(SearchCriteria criteria, Pageable pageable, Dto dto) {
            Specification<ENTITY> specification = new SearchCriteriaSpecification<>(criteria);
            Page<ENTITY> page = getSpecificationRepository().findAll(specification, pageable);
            return page.map(e -> this.map(e, getClass(dto)));
        }

        JpaSpecificationExecutor<ENTITY> getSpecificationRepository();
    }

    interface DtoFetchableById<ID, ENTITY> extends ClassFetchableById<ID, ENTITY>, DtoFetchableSupport {
        default <T> T fetchById(@NonNull ID id, Dto dto) {
            ENTITY entity = getRepository().findById(id)
                    .orElseThrow(() -> new NotFoundException("object not found with id: " + id));
            return this.map(entity, getClass(dto));
        }

        JpaRepository<ENTITY, ID> getRepository();

    }

    @SuppressWarnings("unchecked")
    interface DtoFetchableSupport {
        default <T> Class<T> getClass(Dto dto) {
            Class<?> clazz = getDtoClasses().get(dto.getClassName());
            if (clazz == null) {
                if (getDefaultDtoClass() == null) {
                    throw new RuntimeException("not found dto class with name: " + dto.getClassName());
                }
                return (Class<T>) getDefaultDtoClass();
            }
            return (Class<T>) clazz;
        }

        Map<String, Class<?>> getDtoClasses();

        Class<?> getDefaultDtoClass();
    }
    @SuppressWarnings("unchecked")
    interface DtoMapperSupport {
        default <T, R> R map(T source, Class<R> destinationType) {
            Function<T, R> mapper = getDtoMapper((Class<T>) source.getClass(), destinationType);
            return mapper.apply(source);
        }

        default <T, R> Function<T, R> getDtoMapper(Class<T> sourceClass, Class<R> destinationClass) {
            AbstractMap.SimpleEntry<Class<T>, Class<R>> key = new AbstractMap.SimpleEntry<>(sourceClass, destinationClass);
            return (Function<T, R>) getDtoMappers().get(key);
        }

        Map<AbstractMap.SimpleEntry<Class<?>, Class<?>>, Function<?, ?>> getDtoMappers();
    }
}
