package pl.app.property.payment.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.property.payment.adapter.out.persistence.maper.PaymentMapper;
import pl.app.property.payment.adapter.out.persistence.model.PaymentEntity;
import pl.app.property.payment.adapter.out.persistence.model.StripePaymentEntity;
import pl.app.property.payment.adapter.out.persistence.repository.PaymentRepository;
import pl.app.property.payment.adapter.out.persistence.repository.StripePaymentRepository;
import pl.app.property.payment.application.domain.exception.PaymentException;
import pl.app.property.payment.application.domain.model.Payment;
import pl.app.property.payment.application.domain.model.StripePayment;
import pl.app.property.payment.application.port.out.LoadPaymentPort;
import pl.app.property.payment.application.port.out.LoadStripePaymentPort;
import pl.app.property.payment.application.port.out.SavePaymentPort;
import pl.app.property.payment.application.port.out.SaveStripePaymentPort;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentPersistenceAdapter implements
        LoadStripePaymentPort,
        SaveStripePaymentPort,
        LoadPaymentPort,
        SavePaymentPort {
    private final PaymentRepository repository;
    private final StripePaymentRepository stripePaymentRepository;
    private final PaymentMapper mapper;

    @Override
    public Payment loadPayment(UUID paymentId) {
        PaymentEntity entity = repository.findById(paymentId)
                .orElseThrow(() -> PaymentException.NotFoundPaymentException.fromId(paymentId));
        return mapper.mapToPayment(entity);
    }

    @Override
    public UUID savePayment(Payment payment) {
        PaymentEntity entity = mapper.mapToPaymentEntity(payment);
        repository.save(entity);
        return entity.getPaymentId();
    }

    @Override
    public StripePayment loadStripePayment(UUID paymentId) {
        StripePaymentEntity entity = stripePaymentRepository.findByPaymentEntity_PaymentId(paymentId)
                .orElseThrow(() -> PaymentException.NotFoundPaymentException.fromId(paymentId));
        return mapper.mapToStripePayment(entity);
    }

    @Override
    public UUID savePayment(StripePayment payment) {
        StripePaymentEntity entity = mapper.mapToStripePaymentEntity(payment);
        stripePaymentRepository.save(entity);
        return entity.getStripePaymentId();
    }
}
