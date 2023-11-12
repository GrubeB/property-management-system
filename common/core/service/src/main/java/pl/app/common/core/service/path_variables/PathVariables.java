package pl.app.common.core.service.path_variables;

import java.util.Map;

public interface PathVariables {
    Map<String, String> getVariables();

    String get(String variableName);
}
