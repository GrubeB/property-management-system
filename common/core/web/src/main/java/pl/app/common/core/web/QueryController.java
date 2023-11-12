package pl.app.common.core.web;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.app.common.core.service.BaseQueryService;
import pl.app.common.core.service.criteria.SearchCriteria;
import pl.app.common.core.service.dto.Dto;
import pl.app.common.core.service.path_variables.PathVariables;

public interface QueryController {
    interface SimpleFetchable {
        interface Full<ID, ENTITY> extends
                FetchableBySearchCriteria<ID, ENTITY>,
                FetchableById<ID, ENTITY> {
            @Override
            BaseQueryService.SimpleFetchable.Full<ID, ENTITY> getService();
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

            BaseQueryService.SimpleFetchable.FetchableBySearchCriteria<ID, ENTITY> getService();
        }

        interface FetchableById<ID, ENTITY> {
            @GetMapping("/{id}")
            default ResponseEntity<ENTITY> fetchById(@PathVariable ID id) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(getService().fetchById(id));
            }

            BaseQueryService.SimpleFetchable.FetchableById<ID, ENTITY> getService();
        }
    }

    interface SimpleFetchableWithFilter {
        interface Full<ID, ENTITY> extends
                FetchableWithFilterBySearchCriteria<ID, ENTITY>,
                FetchableById<ID, ENTITY> {
            @Override
            BaseQueryService.SimpleFetchable.Full<ID, ENTITY> getService();
        }

        @Validated
        interface FetchableWithFilterBySearchCriteria<ID, ENTITY> extends
                ControllerSupport.FilterSupport {
            @GetMapping
            default ResponseEntity<Page<ENTITY>> fetchAllBySearchCriteriaAndPageable(PathVariables pathVariables,
                                                                                     @Valid SearchCriteria searchCriteria,
                                                                                     Pageable pageable) {
                this.filterByParentIds(searchCriteria, pathVariables, getParentFilterMap());
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(getService().fetchByCriteria(searchCriteria, pageable));
            }

            BaseQueryService.SimpleFetchable.FetchableBySearchCriteria<ID, ENTITY> getService();
        }

        interface FetchableById<ID, ENTITY> {
            @GetMapping("/{id}")
            default ResponseEntity<ENTITY> fetchById(@PathVariable ID id) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(getService().fetchById(id));
            }

            BaseQueryService.SimpleFetchable.FetchableById<ID, ENTITY> getService();
        }
    }

    interface DtoFetchable {
        interface Full<ID, ENTITY> extends
                DtoFetchableBySearchCriteria<ID, ENTITY>,
                DtoFetchableById<ID, ENTITY> {
            @Override
            BaseQueryService.DtoFetchable.Full<ID, ENTITY> getService();
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

            BaseQueryService.DtoFetchable.DtoFetchableBySearchCriteria<ID, ENTITY> getService();
        }

        interface DtoFetchableById<ID, ENTITY> {
            @GetMapping("/{id}")
            default ResponseEntity<?> fetchByIdAndDto(@PathVariable ID id,
                                                      Dto dto) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(getService().fetchById(id, dto));
            }

            BaseQueryService.DtoFetchable.DtoFetchableById<ID, ENTITY> getService();
        }
    }

    interface DtoFetchableWithFilter {
        interface Full<ID, ENTITY> extends
                DtoFetchableWithFilterBySearchCriteria<ID, ENTITY>,
                DtoFetchableById<ID, ENTITY> {
            @Override
            BaseQueryService.DtoFetchable.Full<ID, ENTITY> getService();
        }

        @Validated
        interface DtoFetchableWithFilterBySearchCriteria<ID, ENTITY> extends
                ControllerSupport.FilterSupport {
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

            BaseQueryService.DtoFetchable.DtoFetchableBySearchCriteria<ID, ENTITY> getService();
        }

        interface DtoFetchableById<ID, ENTITY> {
            @GetMapping("/{id}")
            default ResponseEntity<?> fetchByIdAndDto(@PathVariable ID id,
                                                      Dto dto) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(getService().fetchById(id, dto));
            }

            BaseQueryService.DtoFetchable.DtoFetchableById<ID, ENTITY> getService();
        }
    }
}
