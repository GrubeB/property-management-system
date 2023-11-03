package pl.app.property.reservation_payment_policy.adapter.out.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.reservation_payment_policy.adapter.out.persistence.repositroy.ReservationPaymentPolicyRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class ReservationPaymentPolicyQueryServiceImpl implements ReservationPaymentPolicyQueryService {
    private final ReservationPaymentPolicyRepository repository;
    private final ReservationPaymentPolicyRepository specificationRepository;
}
