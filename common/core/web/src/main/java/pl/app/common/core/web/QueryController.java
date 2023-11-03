package pl.app.common.core.web;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.app.common.core.service.QueryService;
import pl.app.common.core.service.criteria.Operator;
import pl.app.common.core.service.criteria.SearchCriteria;
import pl.app.common.core.service.criteria.SearchCriteriaItem;
import pl.app.common.core.service.dto.Dto;
import pl.app.common.core.service.path_variables.PathVariables;

import java.util.Map;

public interface QueryController {

    interface Fetchable<ID, ENTITY> extends
            FetchableBySearchCriteria<ID, ENTITY>,
            FetchableById<ID, ENTITY> {
    }

    interface FetchableWithFilter<ID, ENTITY> extends
            FetchableWithFilterBySearchCriteria<ID, ENTITY>,
            FetchableById<ID, ENTITY> {
    }

    interface DtoFetchable<ID, ENTITY> extends
            DtoFetchableBySearchCriteria<ID, ENTITY>,
            DtoFetchableById<ID, ENTITY> {
    }

    interface DtoFetchableWithFilter<ID, ENTITY> extends
            DtoFetchableWithFilterBySearchCriteria<ID, ENTITY>,
            DtoFetchableById<ID, ENTITY> {
    }


    interface FetchableById<ID, ENTITY> {
        @GetMapping("/{id}")
        default ResponseEntity<ENTITY> fetchById(@PathVariable ID id) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(getService().fetchById(id));
        }

        QueryService.Fetchable<ID, ENTITY> getService();
    }

    interface DtoFetchableById<ID, ENTITY> {
        @GetMapping("/{id}")
        default ResponseEntity<?> fetchByIdAndDto(@PathVariable ID id,
                                                  Dto dto) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(getService().fetchById(id, dto));
        }

        QueryService.DtoFetchable<ID, ENTITY> getService();
    }

    @Validated
    interface FetchableBySearchCriteria<ID, ENTITY> {
        @GetMapping
        default ResponseEntity<Page<ENTITY>> fetchAllBySearchCriteriaAndPageable(@Valid SearchCriteria searchCriteria,
                                                                                 Pageable pageable) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(getService().fetchByCriteria(searchCriteria, pageable));
        }

        QueryService.Fetchable<ID, ENTITY> getService();
    }

    @Validated
    interface FetchableWithFilterBySearchCriteria<ID, ENTITY> extends FetchableWithFilterSupport {
        @GetMapping
        default ResponseEntity<Page<ENTITY>> fetchAllBySearchCriteriaAndPageable(PathVariables pathVariables,
                                                                                 @Valid SearchCriteria searchCriteria,
                                                                                 Pageable pageable) {
            this.filterByParentIds(searchCriteria, pathVariables, getParentFilterMap());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(getService().fetchByCriteria(searchCriteria, pageable));
        }

        QueryService.Fetchable<ID, ENTITY> getService();
    }


    @Validated
    interface DtoFetchableBySearchCriteria<ID, ENTITY> {
        @GetMapping
        default ResponseEntity<Page<?>> fetchAllBySearchCriteriaAndPageableAndDto(@Valid SearchCriteria searchCriteria,
                                                                                  Pageable pageable,
                                                                                  Dto dto) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(getService().fetchByCriteria(searchCriteria, pageable, dto));
        }

        QueryService.DtoFetchable<ID, ENTITY> getService();
    }

    @Validated
    interface DtoFetchableWithFilterBySearchCriteria<ID, ENTITY> extends FetchableWithFilterSupport {
        @GetMapping
        default ResponseEntity<Page<?>> fetchAllBySearchCriteriaAndPageableAndDto(PathVariables pathVariables,
                                                                                  @Valid SearchCriteria searchCriteria,
                                                                                  Pageable pageable,
                                                                                  Dto dto) {
            this.filterByParentIds(searchCriteria, pathVariables, getParentFilterMap());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(getService().fetchByCriteria(searchCriteria, pageable, dto));
        }

        QueryService.DtoFetchable<ID, ENTITY> getService();
    }

    interface FetchableWithFilterSupport {
        default void filterByParentIds(SearchCriteria searchCriteria, PathVariables pathVariables, Map<String, String> parentFilterMap) {
            parentFilterMap.forEach((key, value) -> {
                String variableValue = pathVariables.getVariables().get(key);
                if (variableValue != null) {
                    searchCriteria.addCriteria(new SearchCriteriaItem(value, Operator.EQUAL, variableValue));
                }
            });
        }

        Map<String, String> getParentFilterMap();
    }
}
