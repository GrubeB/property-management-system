package pl.app.file.file_storage.service;

import pl.app.file.file_storage.domain.FileContent;
import pl.app.file.file_storage.domain.FileLocation;
import pl.app.file.file_storage.domain.FileOperationResponse;

import java.util.Optional;


public interface FileStorageService {
    Optional<FileContent> get(FileLocation fileLocation);

    FileOperationResponse save(FileContent fileContent);

    FileOperationResponse delete(FileLocation fileLocation);

    boolean exists(FileLocation fileLocation);
}
