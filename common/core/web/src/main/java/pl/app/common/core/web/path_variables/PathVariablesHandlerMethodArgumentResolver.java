package pl.app.common.core.web.path_variables;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;
import pl.app.common.core.service.path_variables.PathVariables;
import pl.app.common.core.service.path_variables.PathVariablesRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// Allows injecting PathVariables instances into controller methods.
class PathVariablesHandlerMethodArgumentResolver implements PathVariablesArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return PathVariables.class.equals(parameter.getParameterType());
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public PathVariables resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        try {
            HttpServletRequest nativeRequest = webRequest.getNativeRequest(HttpServletRequest.class);
            if (Objects.nonNull(nativeRequest) &&
                    nativeRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE) instanceof Map<?, ?> attributesHashMap) {
                return PathVariablesRequest.of(new HashMap<>((Map<String, String>) attributesHashMap));
            }
            return PathVariablesRequest.of(new HashMap<>());
        } catch (Exception e) {
            return PathVariablesRequest.of(new HashMap<>());
        }
    }
}
