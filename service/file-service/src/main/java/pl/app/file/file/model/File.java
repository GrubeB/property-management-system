package pl.app.file.file.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_file")
public class File {
    @Id
    private String id;
    @Column(nullable = false)
    private String fileName;
    @Column(name = "content_type")
    private String contentType;
    @Column(name = "size")
    private Integer size;
    @Column(name = "storage_directory_name")
    private String storageDirectoryName;
    @Column(name = "storage_file_name")
    private String storageFileName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        File file = (File) o;
        return id != null && Objects.equals(id, file.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
