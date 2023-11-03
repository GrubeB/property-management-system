package pl.app.common.security.auditing;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditorConfig {
    @Bean
    public AuditorAware<String> auditorProvider(AuthenticationProvider provider) {
        return () -> Optional.of(provider.getCurrentUserName().orElse("-"));
    }
}