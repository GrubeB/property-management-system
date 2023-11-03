package pl.app.mail.template.adapter.out.persistence.model;

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
@Table(name = "t_template_resource")
public class TemplateResourceEntity {
    @Id
    private String resourceId;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "file_id", nullable = false)
    private String fileId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TemplateResourceEntity that = (TemplateResourceEntity) o;
        return getResourceId() != null && Objects.equals(getResourceId(), that.getResourceId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
