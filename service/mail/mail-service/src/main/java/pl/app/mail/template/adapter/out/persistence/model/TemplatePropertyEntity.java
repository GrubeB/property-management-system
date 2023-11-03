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
@Table(name = "t_template_property")
public class TemplatePropertyEntity {
    @Id
    private String propertyId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "default_value", nullable = false)
    private String defaultValue;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TemplatePropertyEntity that = (TemplatePropertyEntity) o;
        return getPropertyId() != null && Objects.equals(getPropertyId(), that.getPropertyId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
