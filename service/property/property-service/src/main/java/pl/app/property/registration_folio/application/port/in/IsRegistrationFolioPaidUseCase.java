package pl.app.property.registration_folio.application.port.in;

public interface IsRegistrationFolioPaidUseCase {
    Boolean isRegistrationFolioPaid(IsRegistrationFolioPaidCommand command);
}
