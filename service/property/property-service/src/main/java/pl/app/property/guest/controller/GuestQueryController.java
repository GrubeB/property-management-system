package pl.app.property.guest.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.core.web.QueryController;
import pl.app.property.guest.model.GuestEntity;
import pl.app.property.guest.service.GuestQueryService;

import java.util.UUID;

@RestController
@RequestMapping(GuestQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class GuestQueryController implements
        QueryController.DtoFetchable.Full<UUID, GuestEntity> {
    public static final String resourceName = "guests";
    public static final String resourcePath = "/api/v1/" + resourceName;

    public final GuestQueryService service;
}
