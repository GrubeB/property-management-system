package pl.app.mail.template.adapter.out.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Objects;
import java.util.Set;


@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_template")
public class TemplateEntity {
    @Id
    private String templateId;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "application_id", nullable = false)
    private String applicationId;

    @Column(name = "template_file_id", nullable = false)
    private String templateFileId;

    @OneToMany(orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "template_id")
    @ToString.Exclude
    private Set<TemplatePropertyEntity> properties;

    @OneToMany(orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "template_id")
    @ToString.Exclude
    private Set<TemplateResourceEntity> resources;

    @OneToMany(orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "template_id")
    @ToString.Exclude
    private Set<TemplateAttachmentEntity> attachments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TemplateEntity that = (TemplateEntity) o;
        return templateId != null && Objects.equals(templateId, that.templateId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
