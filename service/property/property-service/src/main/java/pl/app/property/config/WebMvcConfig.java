package pl.app.property.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.app.common.core.web.criteria.SearchCriteriaArgumentResolver;
import pl.app.common.core.web.criteria.SearchCriteriaArgumentResolverConfig;
import pl.app.common.core.web.dto.DtoArgumentResolver;
import pl.app.common.core.web.dto.DtoArgumentResolverConfig;
import pl.app.common.core.web.path_variables.PathVariablesArgumentResolver;
import pl.app.common.core.web.path_variables.PathVariablesArgumentResolverConfig;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Import({
        DtoArgumentResolverConfig.class,
        SearchCriteriaArgumentResolverConfig.class,
        PathVariablesArgumentResolverConfig.class
})
public class WebMvcConfig implements WebMvcConfigurer {
    private final DtoArgumentResolver dtoArgumentResolver;
    private final SearchCriteriaArgumentResolver searchCriteriaArgumentResolver;
    private final PathVariablesArgumentResolver pathVariablesArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(dtoArgumentResolver);
        resolvers.add(searchCriteriaArgumentResolver);
        resolvers.add(pathVariablesArgumentResolver);
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }
}
