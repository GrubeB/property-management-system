package pl.app.file.file.command;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateFileFromBase64Command implements Serializable {
    @NotNull
    private String content;
    @NotNull
    private String fileName;
    private String applicationId;
}
