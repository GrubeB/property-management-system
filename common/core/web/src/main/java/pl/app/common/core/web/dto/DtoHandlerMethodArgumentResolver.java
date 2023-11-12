package pl.app.common.core.web.dto;


import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import pl.app.common.core.service.dto.Dto;
import pl.app.common.core.service.dto.DtoRequest;

// Allows injecting Dto instances into controller methods.
class DtoHandlerMethodArgumentResolver implements DtoArgumentResolver {
    private final static String DTO_CLASS_PARAMETER_NAME = "dto";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Dto.class.equals(parameter.getParameterType());
    }

    @NonNull
    @Override
    public Dto resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String dtoClassNameParam = webRequest.getParameter(DTO_CLASS_PARAMETER_NAME);
        return DtoRequest.of(dtoClassNameParam);
    }


}
