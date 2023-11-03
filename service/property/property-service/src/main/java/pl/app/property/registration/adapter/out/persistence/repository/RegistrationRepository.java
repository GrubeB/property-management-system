package pl.app.property.registration.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import pl.app.property.registration.adapter.out.persistence.model.RegistrationEntity;

import java.util.Optional;
import java.util.UUID;

public interface RegistrationRepository extends
        JpaRepository<RegistrationEntity, UUID>,
        JpaSpecificationExecutor<RegistrationEntity> {
    @Query("select r from RegistrationEntity r where r.registrationFolio.registrationFolioId = ?1")
    Optional<RegistrationEntity> findByRegistrationFolio_RegistrationFolioId(UUID registrationFolioId);

}
