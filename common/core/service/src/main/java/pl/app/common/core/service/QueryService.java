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
import pl.app.common.core.service.mapper.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public interface QueryService {
    interface Full<ID, ENTITY> extends
            SimpleFetchable.Full<ID, ENTITY>,
            ClassFetchable.Full<ID, ENTITY>,
            DtoFetchable.Full<ID, ENTITY> {
    }

    interface SimpleFetchable {
        interface Full<ID, ENTITY> extends
                FetchableAll<ID, ENTITY>,
                FetchableByPageable<ID, ENTITY>,
                FetchableByCriteria<ID, ENTITY>,
                FetchableBySearchCriteria<ID, ENTITY>,
                FetchableById<ID, ENTITY>,
                BaseQueryService.SimpleFetchable.Full<ID, ENTITY> {
        }

        interface FetchableAll<ID, ENTITY> extends
                BaseQueryService.SimpleFetchable.FetchableAll<ID, ENTITY> {
            @Override
            default List<ENTITY> fetchAll() {
                return getRepository().findAll();
            }

            JpaRepository<ENTITY, ID> getRepository();
        }

        interface FetchableByPageable<ID, ENTITY> extends
                BaseQueryService.SimpleFetchable.FetchableByPageable<ID, ENTITY> {
            @Override
            default Page<ENTITY> fetchByPageable(Pageable pageable) {
                return getRepository().findAll(pageable);
            }

            JpaRepository<ENTITY, ID> getRepository();
        }

        interface FetchableByCriteria<ID, ENTITY> extends
                BaseQueryService.SimpleFetchable.FetchableByCriteria<ID, ENTITY> {
            @Override
            default List<ENTITY> fetchByCriteria(Specification<ENTITY> specification) {
                return getSpecificationRepository().findAll(specification);
            }

            @Override
            default Page<ENTITY> fetchByCriteria(Specification<ENTITY> specification, Pageable pageable) {
                return getSpecificationRepository().findAll(specification, pageable);
            }

            JpaSpecificationExecutor<ENTITY> getSpecificationRepository();
        }

        interface FetchableBySearchCriteria<ID, ENTITY> extends
                BaseQueryService.SimpleFetchable.FetchableBySearchCriteria<ID, ENTITY> {
            @Override
            default List<ENTITY> fetchByCriteria(SearchCriteria criteria) {
                Specification<ENTITY> specification = new SearchCriteriaSpecification<>(criteria);
                return getSpecificationRepository().findAll(specification);
            }

            @Override
            default Page<ENTITY> fetchByCriteria(SearchCriteria criteria, Pageable pageable) {
                Specification<ENTITY> specification = new SearchCriteriaSpecification<>(criteria);
                return getSpecificationRepository().findAll(specification, pageable);
            }

            JpaSpecificationExecutor<ENTITY> getSpecificationRepository();
        }

        interface FetchableById<ID, ENTITY> extends
                BaseQueryService.SimpleFetchable.FetchableById<ID, ENTITY> {
            @Override
            default ENTITY fetchById(@NonNull ID id) {
                return getRepository().findById(id)
                        .orElseThrow(() -> new NotFoundException("object not found with id: " + id));
            }

            JpaRepository<ENTITY, ID> getRepository();
        }
    }

    interface ClassFetchable {
        interface Full<ID, ENTITY> extends
                ClassFetchableAll<ID, ENTITY>,
                ClassFetchableByPageable<ID, ENTITY>,
                ClassFetchableByCriteria<ID, ENTITY>,
                ClassFetchableBySearchCriteria<ID, ENTITY>,
                ClassFetchableById<ID, ENTITY>,
                BaseQueryService.ClassFetchable.Full<ID, ENTITY> {
        }

        interface ClassFetchableAll<ID, ENTITY> extends
                BaseQueryService.ClassFetchable.ClassFetchableAll<ID, ENTITY>,
                SimpleFetchable.FetchableAll<ID, ENTITY>,
                ServiceSupport.MapperSupport {
            @Override
            default <T> List<T> fetchAll(Class<T> dtoClass) {
                List<ENTITY> entities = getRepository().findAll();
                return entities.stream()
                        .map(entity -> getMapper().map(entity, dtoClass))
                        .collect(Collectors.toList());
            }

            JpaRepository<ENTITY, ID> getRepository();

            Mapper getMapper();
        }

        interface ClassFetchableByPageable<ID, ENTITY> extends
                BaseQueryService.ClassFetchable.ClassFetchableByPageable<ID, ENTITY>,
                SimpleFetchable.FetchableByPageable<ID, ENTITY>,
                ServiceSupport.MapperSupport {
            @Override
            default <T> Page<T> fetchByPageable(Pageable pageable, Class<T> dtoClass) {
                Page<ENTITY> page = getRepository().findAll(pageable);
                return page.map(e -> getMapper().map(e, dtoClass));
            }

            JpaRepository<ENTITY, ID> getRepository();

        }

        interface ClassFetchableByCriteria<ID, ENTITY> extends
                BaseQueryService.ClassFetchable.ClassFetchableByCriteria<ID, ENTITY>,
                SimpleFetchable.FetchableByCriteria<ID, ENTITY>,
                ServiceSupport.MapperSupport {
            @Override
            default <T> List<T> fetchByCriteria(Specification<ENTITY> specification, Class<T> dtoClass) {
                List<ENTITY> entities = getSpecificationRepository().findAll(specification);
                return entities.stream()
                        .map(e -> getMapper().map(e, dtoClass))
                        .collect(Collectors.toList());
            }

            @Override
            default <T> Page<T> fetchByCriteria(Specification<ENTITY> specification, Pageable pageable, Class<T> dtoClass) {
                Page<ENTITY> page = getSpecificationRepository().findAll(specification, pageable);
                return page.map(e -> getMapper().map(e, dtoClass));
            }


            JpaSpecificationExecutor<ENTITY> getSpecificationRepository();
        }

        interface ClassFetchableBySearchCriteria<ID, ENTITY> extends
                BaseQueryService.ClassFetchable.ClassFetchableBySearchCriteria<ID, ENTITY>,
                SimpleFetchable.FetchableBySearchCriteria<ID, ENTITY>,
                ServiceSupport.MapperSupport {
            @Override
            default <T> List<T> fetchByCriteria(SearchCriteria criteria, Class<T> dtoClass) {
                Specification<ENTITY> specification = new SearchCriteriaSpecification<>(criteria);
                List<ENTITY> entities = getSpecificationRepository().findAll(specification);
                return entities.stream()
                        .map(e -> getMapper().map(e, dtoClass))
                        .collect(Collectors.toList());
            }

            @Override
            default <T> Page<T> fetchByCriteria(SearchCriteria criteria, Pageable pageable, Class<T> dtoClass) {
                Specification<ENTITY> specification = new SearchCriteriaSpecification<>(criteria);
                Page<ENTITY> page = getSpecificationRepository().findAll(specification, pageable);
                return page.map(e -> getMapper().map(e, dtoClass));
            }

            JpaSpecificationExecutor<ENTITY> getSpecificationRepository();
        }

        interface ClassFetchableById<ID, ENTITY> extends
                BaseQueryService.ClassFetchable.ClassFetchableById<ID, ENTITY>,
                SimpleFetchable.FetchableById<ID, ENTITY>,
                ServiceSupport.MapperSupport {
            @Override
            default <T> T fetchById(@NonNull ID id, Class<T> dtoClass) {
                ENTITY entity = getRepository().findById(id)
                        .orElseThrow(() -> new NotFoundException("object not found with id: " + id));
                return getMapper().map(entity, dtoClass);
            }

            JpaRepository<ENTITY, ID> getRepository();
        }
    }

    interface DtoFetchable {
        interface Full<ID, ENTITY> extends
                DtoFetchableAll<ID, ENTITY>,
                DtoFetchableByPageable<ID, ENTITY>,
                DtoFetchableByCriteria<ID, ENTITY>,
                DtoFetchableBySearchCriteria<ID, ENTITY>,
                DtoFetchableById<ID, ENTITY>,
                BaseQueryService.DtoFetchable.Full<ID, ENTITY> {
        }

        interface DtoFetchableAll<ID, ENTITY> extends
                BaseQueryService.DtoFetchable.DtoFetchableAll<ID, ENTITY>,
                ClassFetchable.ClassFetchableAll<ID, ENTITY>,
                ServiceSupport.DtoSupport {
            @Override
            default <T> List<T> fetchAll(Dto dtoClass) {
                List<ENTITY> entities = getRepository().findAll();
                return entities.stream()
                        .map(entity -> (T) getMapper().<ENTITY, T>map(entity, getClass(dtoClass)))
                        .collect(Collectors.toList());
            }

            JpaRepository<ENTITY, ID> getRepository();
        }

        interface DtoFetchableByPageable<ID, ENTITY> extends
                BaseQueryService.DtoFetchable.DtoFetchableByPageable<ID, ENTITY>,
                ClassFetchable.ClassFetchableByPageable<ID, ENTITY>,
                ServiceSupport.DtoSupport {
            @Override
            default <T> Page<T> fetchByPageable(Pageable pageable, Dto dto) {
                Page<ENTITY> page = getRepository().findAll(pageable);
                return page.map(e -> getMapper().map(e, getClass(dto)));
            }

            JpaRepository<ENTITY, ID> getRepository();
        }

        interface DtoFetchableByCriteria<ID, ENTITY> extends
                BaseQueryService.DtoFetchable.DtoFetchableByCriteria<ID, ENTITY>,
                ClassFetchable.ClassFetchableByCriteria<ID, ENTITY>,
                ServiceSupport.DtoSupport {
            @Override
            default <T> List<T> fetchByCriteria(Specification<ENTITY> specification, Dto dtoClass) {
                List<ENTITY> entities = getSpecificationRepository().findAll(specification);
                return entities.stream()
                        .map(entity -> (T) getMapper().<ENTITY, T>map(entity, getClass(dtoClass)))
                        .collect(Collectors.toList());
            }

            @Override
            default <T> Page<T> fetchByCriteria(Specification<ENTITY> specification, Pageable pageable, Dto dto) {
                Page<ENTITY> page = getSpecificationRepository().findAll(specification, pageable);
                return page.map(e -> getMapper().map(e, getClass(dto)));
            }

            JpaSpecificationExecutor<ENTITY> getSpecificationRepository();
        }

        interface DtoFetchableBySearchCriteria<ID, ENTITY> extends
                BaseQueryService.DtoFetchable.DtoFetchableBySearchCriteria<ID, ENTITY>,
                ClassFetchable.ClassFetchableBySearchCriteria<ID, ENTITY>,
                ServiceSupport.DtoSupport {
            @Override
            default <T> List<T> fetchByCriteria(SearchCriteria criteria, Dto dtoClass) {
                Specification<ENTITY> specification = new SearchCriteriaSpecification<>(criteria);
                List<ENTITY> entities = getSpecificationRepository().findAll(specification);
                return entities.stream()
                        .map(entity -> (T) getMapper().<ENTITY, T>map(entity, getClass(dtoClass)))
                        .collect(Collectors.toList());
            }

            @Override
            default <T> Page<T> fetchByCriteria(SearchCriteria criteria, Pageable pageable, Dto dto) {
                Specification<ENTITY> specification = new SearchCriteriaSpecification<>(criteria);
                Page<ENTITY> page = getSpecificationRepository().findAll(specification, pageable);
                return page.map(e -> getMapper().map(e, getClass(dto)));
            }

            JpaSpecificationExecutor<ENTITY> getSpecificationRepository();
        }

        interface DtoFetchableById<ID, ENTITY> extends
                BaseQueryService.DtoFetchable.DtoFetchableById<ID, ENTITY>,
                ClassFetchable.ClassFetchableById<ID, ENTITY>,
                ServiceSupport.DtoSupport {
            @Override
            default <T> T fetchById(@NonNull ID id, Dto dto) {
                ENTITY entity = getRepository().findById(id)
                        .orElseThrow(() -> new NotFoundException("object not found with id: " + id));
                return getMapper().map(entity, getClass(dto));
            }

            JpaRepository<ENTITY, ID> getRepository();

        }
    }
}
