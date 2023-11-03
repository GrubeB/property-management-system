package pl.app.property.accommodation_type.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.common.core.model.ParentEntity;
import pl.app.property.accommodation.adapter.out.persistence.model.AccommodationEntity;
import pl.app.property.property.model.PropertyEntity;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_accommodation_type")
public class AccommodationTypeEntity extends AbstractEntity<UUID> implements
        ParentEntity<PropertyEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "type_id", nullable = false)
    private UUID accommodationTypeId;
    private String name;
    private String abbreviation;
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender_room_type")
    private GenderRoomType genderRoomType;
    @Enumerated(EnumType.STRING)
    @Column(name = "room_type")
    private RoomType roomType;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "accommodationType",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Builder.Default
    private Set<AccommodationTypeBedEntity> beds = new LinkedHashSet<>();

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "accommodationType",
            orphanRemoval = true)
    @Builder.Default
    private Set<AccommodationTypeImageEntity> images = new LinkedHashSet<>();

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "accommodationType",
            orphanRemoval = true)
    @Builder.Default
    private Set<AccommodationEntity> accommodation = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private PropertyEntity property;

    public void setBeds(Set<AccommodationTypeBedEntity> beds) {
        this.beds.clear();
        beds.stream()
                .peek(e -> e.setAccommodationType(this))
                .forEach(this.beds::add);
    }

    public void setImages(Set<AccommodationTypeImageEntity> images) {
        this.images.clear();
        images.stream()
                .peek(e -> e.setAccommodationType(this))
                .forEach(this.images::add);
    }

    @Override
    public UUID getId() {
        return accommodationTypeId;
    }

    @Override
    public PropertyEntity getParent() {
        return property;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AccommodationTypeEntity that = (AccommodationTypeEntity) o;
        return accommodationTypeId != null && Objects.equals(accommodationTypeId, that.accommodationTypeId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
