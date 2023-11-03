package pl.app.common.core.web.dto;

import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import pl.app.common.core.service.dto.Dto;

public interface DtoArgumentResolver extends HandlerMethodArgumentResolver {
    @NonNull
    @Override
    Dto resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
                        NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory);
}
