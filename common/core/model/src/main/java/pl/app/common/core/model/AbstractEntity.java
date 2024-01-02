package pl.app.common.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.MappedSuperclass;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
public abstract class AbstractEntity<ID extends Serializable> extends Audit implements Persistable<ID> {
    @Override
    @JsonIgnore
    public boolean isNew() {
        return null == this.getId();
    }

    @Override
    @JsonIgnore
    abstract public ID getId();
}
