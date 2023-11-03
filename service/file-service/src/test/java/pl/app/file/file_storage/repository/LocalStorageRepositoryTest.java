package pl.app.file.file_storage.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import pl.app.file.file_storage.domain.FileContent;
import pl.app.file.file_storage.domain.FileLocation;

import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocalStorageRepositoryTest {
    @InjectMocks
    private LocalStorageRepository repository;
    @Mock
    private Environment environment;

    private static final String testPath = "localDirectory/test/" + UUID.randomUUID();

    @BeforeEach
    void initialize() throws IOException {
        when(environment.getRequiredProperty(any())).thenReturn(testPath);
        repository.init();
    }

    @Test
    public void whenFileContentWithoutName_thenShouldThrowException() {
        // given
        FileContent fileContent = FileContent.builder()
                .directory("test2/test3/")
                .content(new byte[]{0, 1, 2}).build();
        // when
        // then
        assertThatThrownBy(() -> repository.save(fileContent)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenValidFileContent_thenShouldSave() throws IOException {
        // given
        FileContent fileContent = FileContent.builder()
                .directory("test2/test3/")
                .name("file31" + UUID.randomUUID())
                .content(new byte[]{0, 1, 2}).build();
        // when
        repository.save(fileContent);
        // then
        assertThat(repository.exists(fileContent)).isTrue();
    }

    @Test
    public void whenFileContentWithoutDirectory_thenShouldSave() throws IOException {
        // given
        FileContent fileContent = FileContent.builder()
                .name("file31" + UUID.randomUUID())
                .content(new byte[]{0, 1, 2}).build();
        // when
        repository.save(fileContent);
        // then
        assertThat(repository.exists(fileContent)).isTrue();
    }

    @Test
    public void whenInValidLocation_thenFileShouldNotExists() {
        // given
        FileLocation fileLocation = FileLocation.builder()
                .directory("test2/test3/")
                .name("file31" + UUID.randomUUID()).build();
        // when
        boolean exists = repository.exists(fileLocation);
        // then
        assertThat(exists).isFalse();
    }

    @Test
    public void whenValidLocation_thenFileShouldExists() throws IOException {
        // given
        byte[] fileBytes = new byte[]{0, 1, 2};
        FileContent fileContent = FileContent.builder()
                .directory("test2/test3/")
                .name("file31" + UUID.randomUUID())
                .content(fileBytes).build();
        repository.save(fileContent);
        // when
        boolean exists = repository.exists(fileContent);
        // then
        assertThat(exists).isTrue();
    }

    @Test
    public void whenValidLocation_thenShouldDeleteFile() throws IOException {
        // given
        byte[] fileBytes = new byte[]{0, 1, 2};
        FileContent fileContent = FileContent.builder()
                .directory("test2/test3/")
                .name("file31" + UUID.randomUUID())
                .content(fileBytes).build();
        repository.save(fileContent);
        // when
        repository.delete(fileContent);
        // then
        assertThatThrownBy(() -> repository.get(fileContent)).isInstanceOf(IOException.class);
    }

    @Test
    public void whenInValidLocation_thenShouldNotDeleteFile() {
        // given
        byte[] fileBytes = new byte[]{0, 1, 2};
        FileContent fileContent = FileContent.builder()
                .directory("test2/test3/")
                .name("file31" + UUID.randomUUID())
                .content(fileBytes).build();
        // when
        // then
        assertThatThrownBy(() -> repository.delete(fileContent)).isInstanceOf(Exception.class);
    }

    @Test
    public void whenValidLocation_thenFileShouldBeFound() throws IOException {
        // given
        byte[] fileBytes = new byte[]{0, 1, 2};
        FileContent fileContent = FileContent.builder()
                .directory("test2/test3/")
                .name("file31" + UUID.randomUUID())
                .content(fileBytes).build();
        repository.save(fileContent);
        // when
        byte[] bytes = repository.get(fileContent);
        // then
        assertThat(bytes).isEqualTo(fileBytes);
    }

    @Test
    public void whenInValidLocation_thenFileShouldNotBeFound() {
        // given
        byte[] fileBytes = new byte[]{0, 1, 2};
        FileContent fileContent = FileContent.builder()
                .name("file31" + UUID.randomUUID())
                .content(fileBytes).build();
        // when
        // then
        assertThatThrownBy(() -> repository.get(fileContent)).isInstanceOf(IOException.class);
    }
}