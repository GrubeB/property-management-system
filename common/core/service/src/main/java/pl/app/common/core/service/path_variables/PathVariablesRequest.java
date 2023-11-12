package pl.app.common.core.service.path_variables;

import java.util.Map;

public class PathVariablesRequest implements PathVariables {
    private final Map<String, String> variables;

    @Override
    public Map<String, String> getVariables() {
        return variables;
    }

    @Override
    public String get(String variableName) {
        return this.variables.get(variableName);
    }

    protected PathVariablesRequest(Map<String, String> variables) {
        this.variables = variables;
    }

    public static PathVariablesRequest of(Map<String, String> variables) {
        return new PathVariablesRequest(variables);
    }
}
