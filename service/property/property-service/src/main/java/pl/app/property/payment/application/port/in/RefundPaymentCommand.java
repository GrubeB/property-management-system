package pl.app.property.payment.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefundPaymentCommand {
    private List<UUID> paymentIds;
}
