package pl.app.file.file.command;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateFileCommand implements Serializable {
    @NotNull
    private byte[] content;
    @NotNull
    private String fileName;
    private String applicationId;
}
