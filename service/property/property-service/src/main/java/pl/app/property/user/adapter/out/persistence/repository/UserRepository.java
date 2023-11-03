package pl.app.property.user.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.app.property.user.adapter.out.persistence.model.UserEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends
        JpaRepository<UserEntity, UUID>,
        JpaSpecificationExecutor<UserEntity> {
    @Query("select u from UserEntity u where u.email = ?1")
    Optional<UserEntity> findByEmail(String email);
}
