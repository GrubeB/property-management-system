package pl.app.property.registration.application.port.in;

public interface CancelRegistrationUseCase {
    void cancelRegistration(CancelRegistrationCommand command);
}
