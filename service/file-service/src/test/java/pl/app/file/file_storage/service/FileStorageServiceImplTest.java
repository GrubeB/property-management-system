package pl.app.file.file_storage.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.app.file.file_storage.domain.FileContent;
import pl.app.file.file_storage.domain.FileLocation;
import pl.app.file.file_storage.domain.FileOperationResponse;
import pl.app.file.file_storage.domain.FileOperationStatus;
import pl.app.file.file_storage.repository.FileStorageRepository;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileStorageServiceImplTest {

    @InjectMocks
    private FileStorageServiceImpl service;

    @Mock
    private FileStorageRepository repository;

    @Test
    void whenValidFileContent_thenShouldSaveFile() {
        // given
        FileContent fileContent = FileContent.builder()
                .name("fileName" + UUID.randomUUID())
                .directory("fileDirectory")
                .content(new byte[]{0, 1, 2}).build();
        // when
        FileOperationResponse response = service.save(fileContent);
        // then
        assertThat(response.getStatus()).isEqualTo(FileOperationStatus.SUCCESS);
    }

    @Test
    void whenFileExists_thenShouldNotSaveFile() {
        // given
        when(repository.exists(any())).thenReturn(true);
        FileContent fileContent = FileContent.builder()
                .name("fileName" + UUID.randomUUID())
                .directory("fileDirectory")
                .content(new byte[]{0, 1, 2}).build();
        // when
        FileOperationResponse response = service.save(fileContent);
        // then
        assertThat(response.getStatus()).isNotEqualTo(FileOperationStatus.SUCCESS);
    }

    @Test
    void whenValidLocation_thenFileShouldBeFound() throws IOException {
        // given
        byte[] data = new byte[]{0, 1, 2};
        when(repository.get(any())).thenReturn(data);
        FileLocation fileLocation = getExampleFileLocation();
        // when
        Optional<FileContent> fileContent = service.get(fileLocation);
        // then
        assertThat(fileContent.get().getContent()).isEqualTo(data);
    }

    @Test
    void whenInValidLocation_thenFileShouldNotBeFound() throws IOException {
        // given
        when(repository.get(any())).thenThrow(new IOException());
        FileLocation fileLocation = getExampleFileLocation();
        // when
        Optional<FileContent> fileContent = service.get(fileLocation);
        // then
        assertThat(fileContent.isEmpty()).isTrue();
    }

    @Test
    void whenValidLocation_thenShouldDeleteFile() throws IOException {
        // given
        when(repository.exists(any())).thenReturn(true);
        FileLocation fileLocation = getExampleFileLocation();
        // when
        FileOperationResponse delete = service.delete(fileLocation);
        // then
        assertThat(delete.getStatus()).isEqualTo(FileOperationStatus.SUCCESS);
    }

    @Test
    void whenInValidLocation_thenShouldNotDeleteFile() throws IOException {
        // given
        when(repository.exists(any())).thenReturn(false);
        FileLocation fileLocation = getExampleFileLocation();
        // when
        FileOperationResponse delete = service.delete(fileLocation);
        // then
        assertThat(delete.getStatus()).isNotEqualTo(FileOperationStatus.SUCCESS);
    }

    private FileLocation getExampleFileLocation() {
        return FileLocation.builder()
                .name("fileName" + UUID.randomUUID())
                .directory("fileDirectory").build();
    }
}