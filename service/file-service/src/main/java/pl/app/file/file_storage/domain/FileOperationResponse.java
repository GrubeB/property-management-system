package pl.app.file.file_storage.domain;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileOperationResponse {
    private FileOperationStatus status;
    private String message;
}
