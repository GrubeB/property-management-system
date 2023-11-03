package pl.app.property.guest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.core.model.AbstractEntity;
import pl.app.common.core.model.RootAware;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_guest_credit_card")
public class CreditCardEntity extends AbstractEntity<UUID> implements RootAware<GuestEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "credit_card_id", nullable = false)
    private UUID creditCardId;

    private Boolean active;
    private String number;
    @Column(name = "name_of_card_holder")
    private String nameOfCardHolder;
    private Instant exp;
    //    private String cvv;
    @ManyToOne
    @JoinColumn(name = "guest_id")
    @ToString.Exclude
    @JsonIgnore
    private GuestEntity guest;

    @Override
    public UUID getId() {
        return creditCardId;
    }

    @Override
    public GuestEntity root() {
        return guest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CreditCardEntity that = (CreditCardEntity) o;
        return creditCardId != null && Objects.equals(creditCardId, that.creditCardId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
