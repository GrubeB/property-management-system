package pl.app.property.registration_folio.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.app.property.registration_folio.adapter.out.persistence.model.RegistrationFolioEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegistrationFolioRepository extends
        JpaRepository<RegistrationFolioEntity, UUID>,
        JpaSpecificationExecutor<RegistrationFolioEntity> {
    @Query("""
            select r from RegistrationFolioEntity r inner join r.partyFolios partyFolios
            where partyFolios.partyFolioId = ?1""")
    Optional<RegistrationFolioEntity> findByPartyFolios_PartyFolioId(UUID partyFolioId);

}
