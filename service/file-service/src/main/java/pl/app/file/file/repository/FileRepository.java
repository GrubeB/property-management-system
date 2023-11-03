package pl.app.file.file.repository;

import pl.app.file.file.model.File;

import java.util.Optional;

public interface FileRepository {
    File save(File entity);

    Optional<File> findById(String fileId);

    void delete(File entity);
}
