package pl.app.common.core.web;

import pl.app.common.core.service.criteria.Operator;
import pl.app.common.core.service.criteria.SearchCriteria;
import pl.app.common.core.service.criteria.SearchCriteriaItem;
import pl.app.common.core.service.path_variables.PathVariables;

import java.util.Map;
import java.util.UUID;

public class ControllerSupport {
    interface FilterSupport {
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

    interface ParentIdResolverSupport<ID> {
        @SuppressWarnings("unchecked")
        default ID getParentId(PathVariables pathVariables) {
            try {
                if (getParentIdPathVariableName() != null) {
                    String parentIdPathVariableName = getParentIdPathVariableName();
                    return (ID) UUID.fromString(pathVariables.get(parentIdPathVariableName));
                }
                throw new RuntimeException("parent id could not be extracted from path");
            } catch (Exception e) {
                throw new RuntimeException("parent id could not be extracted from path");
            }
        }

        String getParentIdPathVariableName();
    }
}
