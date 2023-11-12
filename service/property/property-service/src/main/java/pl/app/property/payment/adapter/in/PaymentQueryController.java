package pl.app.property.payment.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.core.web.QueryController;
import pl.app.property.payment.adapter.out.persistence.model.PaymentEntity;
import pl.app.property.payment.adapter.out.query.PaymentQueryService;

import java.util.UUID;

@RestController
@RequestMapping(PaymentQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class PaymentQueryController implements
        QueryController.DtoFetchable.Full<UUID, PaymentEntity> {
    public static final String resourceName = "payments";
    public static final String resourcePath = "/api/v1/" + resourceName;
    public final PaymentQueryService service;
}
