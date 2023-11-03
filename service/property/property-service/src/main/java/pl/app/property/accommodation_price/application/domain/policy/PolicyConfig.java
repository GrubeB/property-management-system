package pl.app.property.accommodation_price.application.domain.policy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Configuration
public class PolicyConfig {

    @Bean
    public Map<AccommodationTypePricePolicyType, AccommodationTypePricePolicy> accommodationTypePricePolicies(List<AccommodationTypePricePolicy> policies) {
        Map<AccommodationTypePricePolicyType, AccommodationTypePricePolicy> messagesByType = new EnumMap<>(AccommodationTypePricePolicyType.class);
        policies.forEach(notificationStrategy -> messagesByType.put(notificationStrategy.getType(), notificationStrategy));
        return messagesByType;
    }
}
