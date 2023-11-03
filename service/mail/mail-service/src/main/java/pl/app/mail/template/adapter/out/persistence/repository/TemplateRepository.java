package pl.app.mail.template.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.app.mail.template.adapter.out.persistence.model.TemplateEntity;

import java.util.Optional;

@Repository
public interface TemplateRepository extends JpaRepository<TemplateEntity, String> {
    @Query("""
            select t from TemplateEntity t
            where upper(t.name) = upper(:name) and t.applicationId = :applicationId
            """)
    Optional<TemplateEntity> findByNameIgnoreCaseAndApplicationId(@Param("name") String name, @Param("applicationId") String applicationId);
}
