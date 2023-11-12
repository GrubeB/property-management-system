package pl.app.common.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import pl.app.common.core.service.criteria.SearchCriteria;
import pl.app.common.core.service.dto.Dto;

import java.util.List;

public interface BaseQueryService {

    interface SimpleFetchable {
        interface Full<ID, ENTITY> extends
                FetchableAll<ID, ENTITY>,
                FetchableByPageable<ID, ENTITY>,
                FetchableByCriteria<ID, ENTITY>,
                FetchableBySearchCriteria<ID, ENTITY>,
                FetchableById<ID, ENTITY> {
        }

        interface FetchableAll<ID, ENTITY> {
            List<ENTITY> fetchAll();
        }

        interface FetchableByPageable<ID, ENTITY> {
            Page<ENTITY> fetchByPageable(Pageable pageable);
        }

        interface FetchableByCriteria<ID, ENTITY> {
            List<ENTITY> fetchByCriteria(Specification<ENTITY> specification);

            Page<ENTITY> fetchByCriteria(Specification<ENTITY> specification, Pageable pageable);
        }

        interface FetchableBySearchCriteria<ID, ENTITY> {
            List<ENTITY> fetchByCriteria(SearchCriteria criteria);

            Page<ENTITY> fetchByCriteria(SearchCriteria criteria, Pageable pageable);
        }

        interface FetchableById<ID, ENTITY> {
            ENTITY fetchById(@NonNull ID id);
        }
    }

    interface ClassFetchable {
        interface Full<ID, ENTITY> extends
                ClassFetchableAll<ID, ENTITY>,
                ClassFetchableByPageable<ID, ENTITY>,
                ClassFetchableByCriteria<ID, ENTITY>,
                ClassFetchableBySearchCriteria<ID, ENTITY>,
                ClassFetchableById<ID, ENTITY> {
        }

        interface ClassFetchableAll<ID, ENTITY> {
            <T> List<T> fetchAll(Class<T> dtoClass);
        }

        interface ClassFetchableByPageable<ID, ENTITY> {
            <T> Page<T> fetchByPageable(Pageable pageable, Class<T> dtoClass);
        }

        interface ClassFetchableByCriteria<ID, ENTITY> {
            <T> List<T> fetchByCriteria(Specification<ENTITY> specification, Class<T> dtoClass);

            <T> Page<T> fetchByCriteria(Specification<ENTITY> specification, Pageable pageable, Class<T> dtoClass);
        }

        interface ClassFetchableBySearchCriteria<ID, ENTITY> {
            <T> List<T> fetchByCriteria(SearchCriteria criteria, Class<T> dtoClass);

            <T> Page<T> fetchByCriteria(SearchCriteria criteria, Pageable pageable, Class<T> dtoClass);
        }

        interface ClassFetchableById<ID, ENTITY> {
            <T> T fetchById(@NonNull ID id, Class<T> dtoClass);
        }
    }

    interface DtoFetchable {
        interface Full<ID, ENTITY> extends
                DtoFetchableAll<ID, ENTITY>,
                DtoFetchableByPageable<ID, ENTITY>,
                DtoFetchableByCriteria<ID, ENTITY>,
                DtoFetchableBySearchCriteria<ID, ENTITY>,
                DtoFetchableById<ID, ENTITY> {
        }

        interface DtoFetchableAll<ID, ENTITY> {
            <T> List<T> fetchAll(Dto dtoClass);
        }

        interface DtoFetchableByPageable<ID, ENTITY> {
            <T> Page<T> fetchByPageable(Pageable pageable, Dto dto);
        }

        interface DtoFetchableByCriteria<ID, ENTITY> {
            <T> List<T> fetchByCriteria(Specification<ENTITY> specification, Dto dtoClass);

            <T> Page<T> fetchByCriteria(Specification<ENTITY> specification, Pageable pageable, Dto dto);
        }

        interface DtoFetchableBySearchCriteria<ID, ENTITY> {
            <T> List<T> fetchByCriteria(SearchCriteria criteria, Dto dtoClass);

            <T> Page<T> fetchByCriteria(SearchCriteria criteria, Pageable pageable, Dto dto);
        }

        interface DtoFetchableById<ID, ENTITY> {
            <T> T fetchById(@NonNull ID id, Dto dto);
        }
    }
}
