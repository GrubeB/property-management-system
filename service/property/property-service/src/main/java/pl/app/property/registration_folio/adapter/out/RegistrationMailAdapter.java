package pl.app.property.registration_folio.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.shared.mail.MailTemplateNames;
import pl.app.mail.sdk.SendMailCommand;
import pl.app.mail.sdk.SendMailHttpClient;
import pl.app.property.config.domain.WebAddressService;
import pl.app.property.guest.model.GuestEntity;
import pl.app.property.guest.service.GuestQueryService;
import pl.app.property.organization.model.OrganizationEntity;
import pl.app.property.payment.adapter.in.PaymentController;
import pl.app.property.property.model.PropertyEntity;
import pl.app.property.property.service.PropertyQueryService;
import pl.app.property.registration_folio.application.port.out.RegistrationMailPort;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RegistrationMailAdapter implements
        RegistrationMailPort {
    private final SendMailHttpClient sendMailHttpClient;
    private final WebAddressService webAddressService;
    private final PropertyQueryService propertyQueryService;
    private final GuestQueryService guestQueryService;

    @Override
    public void sendMail(UUID paymentId, UUID guestId, UUID propertyId) {
        PropertyEntity property = propertyQueryService.fetchById(propertyId);
        OrganizationEntity organization = property.getOrganization();
        GuestEntity guest = guestQueryService.fetchById(guestId);
        String paymentLink = webAddressService.getWebAddress() +
                PaymentController.controllerPath +
                PaymentController.createCheckoutSessionPath +
                "/" + paymentId;

        SendMailCommand sendMailCommand = SendMailCommand.builder()
                .to(guest.getEmail())
                .subject("Payment")
                .templateName(MailTemplateNames.PAYMENT_CHECKOUT_MAIL_TEMPLATE.getTemplateName())
                .properties(List.of(
                        new SendMailCommand.Property("company_name", organization.getName()),
                        new SendMailCommand.Property("payment_id", paymentId.toString()),
                        new SendMailCommand.Property("hotel_name", property.getPropertyDetails().getName()),
                        new SendMailCommand.Property("payment_link", paymentLink),
                        new SendMailCommand.Property("company_url", "company_url")
                ))
                .build();
        sendMailHttpClient.sendMailAsync(sendMailCommand);
    }
}
