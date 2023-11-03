package pl.app.file.file.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.util.FileUtils;
import pl.app.common.util.model.CustomByteArrayResource;
import pl.app.file.file.command.CreateFileCommand;
import pl.app.file.file.command.CreateFileFromBase64Command;
import pl.app.file.file.exception.FileException;
import pl.app.file.file.model.File;
import pl.app.file.file.repository.FileRepository;
import pl.app.file.file_storage.domain.FileContent;
import pl.app.file.file_storage.domain.FileLocation;
import pl.app.file.file_storage.domain.FileOperationResponse;
import pl.app.file.file_storage.domain.FileOperationStatus;
import pl.app.file.file_storage.service.FileStorageService;

import java.util.Base64;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final FileStorageService fileStorageService;

    @Override
    public File createFile(CreateFileFromBase64Command command) {
        byte[] byteContent = Base64.getDecoder().decode(command.getContent());
        return createFile(new CreateFileCommand(byteContent, command.getFileName(), command.getApplicationId()));
    }

    @Override
    public File createFile(CreateFileCommand command) {
        final String storageDirectoryName = command.getApplicationId();
        final String storageFileName = FileUtils.getUniqueFileName(command.getFileName());

        File file = File.builder()
                .id(UUID.randomUUID().toString())
                .fileName(command.getFileName())
                .contentType(FileUtils.getExtension(command.getFileName()).orElse(""))
                .size(command.getContent().length)
                .storageFileName(storageFileName)
                .storageDirectoryName(storageDirectoryName)
                .build();

        FileContent fileContent = FileContent.builder()
                .content(command.getContent())
                .name(file.getStorageFileName())
                .directory(file.getStorageDirectoryName())
                .build();
        FileOperationResponse saveOperationResponse = fileStorageService.save(fileContent);
        if (!saveOperationResponse.getStatus().equals(FileOperationStatus.SUCCESS)) {
            throw new RuntimeException(saveOperationResponse.getMessage());
        }

        return fileRepository.save(file);
    }


    @Override
    @Transactional(readOnly = true)
    public File fetchFileById(String fileId) {
        return fileRepository.findById(fileId).orElseThrow(() -> FileException.NotFoundFileException.fromId(fileId));
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] fetchFileContentById(String fileId) {
        File file = fileRepository.findById(fileId).orElseThrow(() -> FileException.NotFoundFileException.fromId(fileId));
        FileLocation fileLocation = new FileLocation(file.getStorageDirectoryName(), file.getStorageFileName());
        FileContent fileContent = fileStorageService.get(fileLocation)
                .orElseThrow(() -> FileException.NotFoundFileStorageException.fromFileLocation(fileLocation));
        return fileContent.getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public Resource fetchFileAsResourceById(String fileId) {
        File file = fileRepository.findById(fileId).orElseThrow(() -> FileException.NotFoundFileException.fromId(fileId));
        FileLocation fileLocation = new FileLocation(file.getStorageDirectoryName(), file.getStorageFileName());
        FileContent fileContent = fileStorageService.get(fileLocation)
                .orElseThrow(() -> FileException.NotFoundFileStorageException.fromFileLocation(fileLocation));
        return new CustomByteArrayResource(fileContent.getContent(), file.getFileName());
    }

    @Override
    public void deleteFileById(String fileId) {
        File file = fileRepository.findById(fileId).orElseThrow(() -> FileException.NotFoundFileException.fromId(fileId));
        FileLocation fileLocation = new FileLocation(file.getStorageDirectoryName(), file.getStorageFileName());
        fileStorageService.delete(fileLocation);
        fileRepository.delete(file);
    }
}
