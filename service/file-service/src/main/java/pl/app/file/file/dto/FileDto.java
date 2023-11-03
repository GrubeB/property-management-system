package pl.app.file.file.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FileDto implements Serializable {
    private String id;
    private String fileName;
    private String contentType;
    private Integer size;
}
