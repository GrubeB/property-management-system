package pl.app.property.accommodation_price_policy.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.core.web.CommandController;
import pl.app.property.accommodation_price_policy.model.AccommodationTypePriceDayOfTheWeekPolicyEntity;
import pl.app.property.accommodation_price_policy.service.AccommodationTypePriceDayOfTheWeekPolicyService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(AccommodationTypePriceDayOfTheWeekPolicyController.resourcePath)
@RequiredArgsConstructor
@Getter
public class AccommodationTypePriceDayOfTheWeekPolicyController implements
        CommandController.CreatableWithParent<UUID, AccommodationTypePriceDayOfTheWeekPolicyEntity>,
        CommandController.Updatable<UUID, AccommodationTypePriceDayOfTheWeekPolicyEntity>,
        CommandController.DeletableById<UUID, AccommodationTypePriceDayOfTheWeekPolicyEntity> {
    public static final String resourceName = "day-of-week-policies";
    public static final String resourcePath = "/api/v1/organizations/{organizationId}/properties/{propertyId}/accommodation-type-price-policies/" + resourceName;

    private final List<String> parentIdPathVariableNames = List.of("propertyId");

    public final AccommodationTypePriceDayOfTheWeekPolicyService service;
}
