package pl.app.common.core.web.dto;

import org.springframework.context.annotation.Bean;

public class DtoArgumentResolverConfig {
    @Bean
    public DtoArgumentResolver dtoArgumentResolver() {
        return new DtoHandlerMethodArgumentResolver();
    }
}
