package pl.app.property.payment.adapter.in;

import com.stripe.model.checkout.Session;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.property.payment.application.port.in.CreatePaymentCheckoutCommand;
import pl.app.property.payment.application.port.in.CreatePaymentCheckoutUseCase;
import pl.app.property.payment.application.port.in.PaymentCheckoutFailedUseCase;
import pl.app.property.payment.application.port.in.PaymentCheckoutSuccessUseCase;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping(PaymentController.controllerPath)
@RequiredArgsConstructor
public class PaymentController {
    public static final String controllerPath = "/api/v1/payments";
    public static final String createCheckoutSessionPath = "/create-checkout";
    public static final String checkoutSessionSuccessPath = "/success";
    public static final String checkoutSessionFailedPath = "/cancel";

    private final CreatePaymentCheckoutUseCase createPaymentCheckoutUseCase;
    private final PaymentCheckoutFailedUseCase paymentCheckoutFailedUseCase;
    private final PaymentCheckoutSuccessUseCase paymentCheckoutSuccessUseCase;

    @GetMapping(createCheckoutSessionPath + "/{paymentId}")
    void createCheckoutSession(@PathVariable UUID paymentId, HttpServletResponse response) throws IOException {
        Session checkoutSession = createPaymentCheckoutUseCase.createCheckoutSession(new CreatePaymentCheckoutCommand(paymentId));
        response.sendRedirect(checkoutSession.getUrl());
    }

    @GetMapping(checkoutSessionSuccessPath + "/{paymentId}")
    ResponseEntity<Void> checkoutSessionSuccess(@PathVariable UUID paymentId) {
        paymentCheckoutSuccessUseCase.checkoutSessionSuccess(paymentId);
        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping(checkoutSessionFailedPath + "/{paymentId}")
    ResponseEntity<Void> checkoutSessionFailed(@PathVariable UUID paymentId) {
        paymentCheckoutFailedUseCase.checkoutSessionFailed(paymentId);
        return ResponseEntity
                .noContent()
                .build();
    }
}
