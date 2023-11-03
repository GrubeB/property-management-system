package pl.app.file.file.service;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import pl.app.file.AbstractClass;
import pl.app.file.file.command.CreateFileCommand;
import pl.app.file.file.model.File;

import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
class FileServiceImplIntegrationTest extends AbstractClass {
    @Autowired
    private FileService fileService;

    @Test
    void whenValidCommand_thenShouldCreateFile() {
        // given
        CreateFileCommand command = getExampleCreateFileCommand();
        // when
        File file = fileService.createFile(command);
        // then
        assertThat(file).isNotNull();
    }

    @Test
    void whenValidFileId_thenFileShouldByFound() {
        // given
        CreateFileCommand command = getExampleCreateFileCommand();
        File f = fileService.createFile(command);
        // when
        File file = fileService.fetchFileById(f.getId());
        // then
        assertThat(file.getFileName()).isEqualTo("fileName");
        assertThat(file.getSize()).isEqualTo(3);
    }

    @Test
    void whenValidFileId_thenFileContentShouldByReturned() {
        // given
        CreateFileCommand command = getExampleCreateFileCommand();
        File file = fileService.createFile(command);
        // when
        byte[] data = fileService.fetchFileContentById(file.getId());
        // then
        assertThat(data).hasSize(3);
        assertThat(data).isEqualTo(new byte[]{1, 2, 3});
    }

    @Test
    void whenValidFileId_thenResourceShouldByReturned() throws IOException {
        // given
        CreateFileCommand command = getExampleCreateFileCommand();
        File file = fileService.createFile(command);
        // when
        Resource resource = fileService.fetchFileAsResourceById(file.getId());
        // then
        assertThat(resource.getFilename()).isEqualTo("fileName");
        assertThat(resource.getContentAsByteArray()).isEqualTo(new byte[]{1, 2, 3});
    }

    @Test
    void whenValidFileId_thenResourceShouldByDeleted() throws IOException {
        // given
        CreateFileCommand command = getExampleCreateFileCommand();
        File file = fileService.createFile(command);
        // when
        fileService.deleteFileById(file.getId());
        // then
        assertThatThrownBy(() -> fileService.fetchFileById(file.getId()))
                .isInstanceOf(RuntimeException.class);
    }

    @NotNull
    private CreateFileCommand getExampleCreateFileCommand() {
        return new CreateFileCommand(new byte[]{1, 2, 3},
                "fileName",
                "test/applicationName" + UUID.randomUUID());
    }
}