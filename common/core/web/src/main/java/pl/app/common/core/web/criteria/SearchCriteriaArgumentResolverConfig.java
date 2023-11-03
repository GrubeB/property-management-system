package pl.app.common.core.web.criteria;

import org.springframework.context.annotation.Bean;

public class SearchCriteriaArgumentResolverConfig {
    @Bean
    public SearchCriteriaArgumentResolver searchCriteriaArgumentResolver() {
        return new SearchCriteriaHandlerMethodArgumentResolver();
    }
}
