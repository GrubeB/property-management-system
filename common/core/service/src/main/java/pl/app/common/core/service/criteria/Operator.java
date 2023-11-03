package pl.app.common.core.service.criteria;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import java.time.*;

@SuppressWarnings("unchecked")
public enum Operator {
    EQUAL {
        public <T> Specification<T> build(SearchCriteriaItem filter) {
            return (root, query, criteriaBuilder) -> {
                Path<Object> attribute = getPath(root, filter.getField());
                Object value = fieldValueCaster.castValueTo(filter.getValue(), attribute.getJavaType());
                return criteriaBuilder.equal(attribute, value);
            };
        }
    },
    NOT_EQUAL {
        public <T> Specification<T> build(SearchCriteriaItem filter) {
            return (root, query, criteriaBuilder) -> {
                Path<Object> attribute = getPath(root, filter.getField());
                Object value = fieldValueCaster.castValueTo(filter.getValue(), attribute.getJavaType());
                return criteriaBuilder.notEqual(attribute, value);
            };
        }
    },
    LIKE {
        public <T> Specification<T> build(SearchCriteriaItem filter) {
            return (root, query, criteriaBuilder) -> {
                @SuppressWarnings("unchecked")
                Path<String> attribute = (Path<String>) (Path<?>) getPath(root, filter.getField()); // XD
                return criteriaBuilder.like(criteriaBuilder.upper(attribute), "%" + filter.getValue().toUpperCase() + "%");
            };
        }
    },
    IN {
        public <T> Specification<T> build(SearchCriteriaItem filter) {
            return (root, query, criteriaBuilder) -> {
                Path<Object> attribute = getPath(root, filter.getField());
                Object values = fieldValueCaster.casValuesTo(filter.getValues(), attribute.getJavaType());
                return criteriaBuilder.in(attribute).value(values);
            };
        }
    },
    BETWEEN {
        public <T> Specification<T> build(SearchCriteriaItem filter) {
            return (root, query, criteriaBuilder) -> {
                Path<Object> attribute = getPath(root, filter.getField());
                Object value = fieldValueCaster.castValueTo(filter.getValue(), attribute.getJavaType());
                Object valueTo = fieldValueCaster.castValueTo(filter.getValueTo(), attribute.getJavaType());

                Class<?> fieldType = attribute.getJavaType();
                if (fieldType == LocalTime.class) {
                    return criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get(filter.getField()), (LocalTime) value),
                            criteriaBuilder.lessThanOrEqualTo(root.get(filter.getField()), (LocalTime) valueTo));
                } else if (fieldType == LocalDate.class) {
                    return criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get(filter.getField()), (LocalDate) value),
                            criteriaBuilder.lessThanOrEqualTo(root.get(filter.getField()), (LocalDate) valueTo));
                } else if (fieldType == LocalDateTime.class) {
                    return criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get(filter.getField()), (LocalDateTime) value),
                            criteriaBuilder.lessThanOrEqualTo(root.get(filter.getField()), (LocalDateTime) valueTo));
                } else if (fieldType == Instant.class) {
                    return criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get(filter.getField()), (Instant) value),
                            criteriaBuilder.lessThanOrEqualTo(root.get(filter.getField()), (Instant) valueTo));
                } else if (fieldType == OffsetDateTime.class) {
                    return criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get(filter.getField()), (OffsetDateTime) value),
                            criteriaBuilder.lessThanOrEqualTo(root.get(filter.getField()), (OffsetDateTime) valueTo));
                } else if (fieldType == ZonedDateTime.class) {
                    return criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get(filter.getField()), (ZonedDateTime) value),
                            criteriaBuilder.lessThanOrEqualTo(root.get(filter.getField()), (ZonedDateTime) valueTo));
                } else if (fieldType == Character.class || fieldType == char.class) {
                    return criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get(filter.getField()), (Character) value),
                            criteriaBuilder.lessThanOrEqualTo(root.get(filter.getField()), (Character) valueTo));
                } else if (fieldType == Short.class || fieldType == short.class
                        || fieldType == Integer.class || fieldType == int.class
                        || fieldType == Long.class || fieldType == long.class
                        || fieldType == Float.class || fieldType == float.class
                        || fieldType == Double.class || fieldType == double.class) {
                    return criteriaBuilder.and(criteriaBuilder.ge(root.get(filter.getField()), (Number) value),
                            criteriaBuilder.le(root.get(filter.getField()), (Number) valueTo));
                }
                logger.info("Can not use between for {} field type.", fieldType);
                return criteriaBuilder.equal(criteriaBuilder.literal(Boolean.TRUE), Boolean.TRUE);
            };
        }
    },
    GREATER_THAN_OR_EQUAL {
        public <T> Specification<T> build(SearchCriteriaItem filter) {
            return (root, query, criteriaBuilder) -> {
                Path<Object> attribute = getPath(root, filter.getField());
                Object value = fieldValueCaster.castValueTo(filter.getValue(), attribute.getJavaType());

                Class<?> fieldType = attribute.getJavaType();
                if (fieldType == LocalTime.class) {
                    return criteriaBuilder.greaterThanOrEqualTo(root.get(filter.getField()), (LocalTime) value);
                } else if (fieldType == LocalDate.class) {
                    return criteriaBuilder.greaterThanOrEqualTo(root.get(filter.getField()), (LocalDate) value);
                } else if (fieldType == LocalDateTime.class) {
                    return criteriaBuilder.greaterThanOrEqualTo(root.get(filter.getField()), (LocalDateTime) value);
                } else if (fieldType == Instant.class) {
                    return criteriaBuilder.greaterThanOrEqualTo(root.get(filter.getField()), (Instant) value);
                } else if (fieldType == OffsetDateTime.class) {
                    return criteriaBuilder.greaterThanOrEqualTo(root.get(filter.getField()), (OffsetDateTime) value);
                } else if (fieldType == ZonedDateTime.class) {
                    return criteriaBuilder.greaterThanOrEqualTo(root.get(filter.getField()), (ZonedDateTime) value);
                } else if (fieldType == Character.class || fieldType == char.class) {
                    return criteriaBuilder.greaterThanOrEqualTo(root.get(filter.getField()), (Character) value);
                } else if (fieldType == Short.class || fieldType == short.class
                        || fieldType == Integer.class || fieldType == int.class
                        || fieldType == Long.class || fieldType == long.class
                        || fieldType == Float.class || fieldType == float.class
                        || fieldType == Double.class || fieldType == double.class) {
                    return criteriaBuilder.ge(root.get(filter.getField()), (Number) value);
                }

                logger.info("Can not use between for {} field type.", fieldType);
                return criteriaBuilder.equal(criteriaBuilder.literal(Boolean.TRUE), Boolean.TRUE);
            };
        }
    },
    LESS_THAN_OR_EQUAL {
        public <T> Specification<T> build(SearchCriteriaItem filter) {
            return (root, query, criteriaBuilder) -> {
                Path<Object> attribute = getPath(root, filter.getField());
                Object value = fieldValueCaster.castValueTo(filter.getValue(), attribute.getJavaType());

                Class<?> fieldType = attribute.getJavaType();
                if (fieldType == LocalTime.class) {
                    return criteriaBuilder.lessThanOrEqualTo(root.get(filter.getField()), (LocalTime) value);
                } else if (fieldType == LocalDate.class) {
                    return criteriaBuilder.lessThanOrEqualTo(root.get(filter.getField()), (LocalDate) value);
                } else if (fieldType == LocalDateTime.class) {
                    return criteriaBuilder.lessThanOrEqualTo(root.get(filter.getField()), (LocalDateTime) value);
                } else if (fieldType == Instant.class) {
                    return criteriaBuilder.lessThanOrEqualTo(root.get(filter.getField()), (Instant) value);
                } else if (fieldType == OffsetDateTime.class) {
                    return criteriaBuilder.lessThanOrEqualTo(root.get(filter.getField()), (OffsetDateTime) value);
                } else if (fieldType == ZonedDateTime.class) {
                    return criteriaBuilder.lessThanOrEqualTo(root.get(filter.getField()), (ZonedDateTime) value);
                } else if (fieldType == Character.class || fieldType == char.class) {
                    return criteriaBuilder.lessThanOrEqualTo(root.get(filter.getField()), (Character) value);
                } else if (fieldType == Short.class || fieldType == short.class
                        || fieldType == Integer.class || fieldType == int.class
                        || fieldType == Long.class || fieldType == long.class
                        || fieldType == Float.class || fieldType == float.class
                        || fieldType == Double.class || fieldType == double.class) {
                    return criteriaBuilder.le(root.get(filter.getField()), (Number) value);
                }
                logger.info("Can not use between for {} field type.", fieldType);
                return criteriaBuilder.equal(criteriaBuilder.literal(Boolean.TRUE), Boolean.TRUE);
            };
        }
    };

    public abstract <T> Specification<T> build(SearchCriteriaItem filter);

    private static final Logger logger = LoggerFactory.getLogger(Operator.class);
    private static final FieldValueCaster fieldValueCaster = FieldValueCaster.INSTANCE;

    @SuppressWarnings("unchecked")
    private static Path<Object> getPath(Root<?> root, String attributeName) {
        Path<Object> path = (Path<Object>) root;
        String[] split = attributeName.split("\\.");
        if (split.length == 1) {
            return root.get(attributeName);
        } else {
            String part = split[0];
            try {
                path = root.join(part, JoinType.LEFT);
            } catch (IllegalArgumentException e) {
                path = path.get(part);
            }
            for (int i = 1; i < split.length; i++) {
                part = split[i];
                try {
                    Join<Object, Object> join = (Join<Object, Object>) path;
                    path = join.join(part, JoinType.LEFT);
                } catch (Exception e) {
                    path = path.get(part);
                }
            }

        }
        return path;
    }
}
