package pl.app.common.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ParentEntity<T> {

    @JsonIgnore
    T getParent();
}