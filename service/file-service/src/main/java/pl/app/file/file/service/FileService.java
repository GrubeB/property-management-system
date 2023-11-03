package pl.app.file.file.service;

import org.springframework.core.io.Resource;
import pl.app.file.file.command.CreateFileCommand;
import pl.app.file.file.command.CreateFileFromBase64Command;
import pl.app.file.file.model.File;

public interface FileService {
    File createFile(CreateFileCommand command);

    File createFile(CreateFileFromBase64Command command);

    void deleteFileById(String fileId);

    File fetchFileById(String fileId);

    byte[] fetchFileContentById(String fileId);

    Resource fetchFileAsResourceById(String fileId);

}
