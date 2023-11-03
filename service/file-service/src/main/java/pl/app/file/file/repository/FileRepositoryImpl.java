package pl.app.file.file.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.app.file.file.model.File;

import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
class FileRepositoryImpl implements FileRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public File save(File entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<File> findById(String fileId) {
        TypedQuery<File> jpqlQuery = entityManager.createQuery("SELECT f FROM File f WHERE f.id=:id", File.class);
        jpqlQuery.setParameter("id", fileId);
        return jpqlQuery.getResultStream().findFirst();
    }

    @Override
    public void delete(File entity) {
        entityManager.remove(entityManager.merge(entity));
    }
}
