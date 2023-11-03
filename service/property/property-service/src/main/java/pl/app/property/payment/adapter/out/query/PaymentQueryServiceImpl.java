package pl.app.property.payment.adapter.out.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.payment.adapter.out.persistence.repository.PaymentRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class PaymentQueryServiceImpl implements PaymentQueryService {
    private final PaymentRepository repository;
    private final PaymentRepository specificationRepository;
}
