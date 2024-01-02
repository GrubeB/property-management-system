package pl.app.property.payment.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class StripePayment {
    private UUID stripePaymentId;
    private Payment payment;
    private String paymentIntent;
    private String sessionCheckoutId;

    public StripePayment(Payment payment, String sessionCheckoutId) {
        this.stripePaymentId = UUID.randomUUID();
        this.payment = payment;
        this.paymentIntent = null;
        this.sessionCheckoutId = sessionCheckoutId;
    }

    public void setPaymentIntent(String paymentIntent) {
        this.paymentIntent = paymentIntent;
    }
}
