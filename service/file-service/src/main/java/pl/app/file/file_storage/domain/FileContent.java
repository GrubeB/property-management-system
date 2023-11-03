package pl.app.file.file_storage.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileContent extends FileLocation {
    private byte[] content;

    public FileContent(String directory, String name, byte[] content) {
        super(directory, name);
        this.content = content;
    }

    public FileContent(FileLocation fileLocation, byte[] content) {
        super(fileLocation.getDirectory(), fileLocation.getDirectory());
        this.content = content;
    }

    public FileContent(FileLocation fileLocation) {
        super(fileLocation.getDirectory(), fileLocation.getDirectory());
    }
}
