package pl.app.common.core.web.path_variables;

import org.springframework.context.annotation.Bean;

public class PathVariablesArgumentResolverConfig {
    @Bean
    public PathVariablesArgumentResolver pathVariablesArgumentResolver() {
        return new PathVariablesHandlerMethodArgumentResolver();
    }
}
