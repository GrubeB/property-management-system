package pl.app.common.core.model.config;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.event.internal.DefaultFlushEntityEventListener;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.FlushEntityEvent;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HibernateListenerConfig {
    private final EntityManagerFactory entityManagerFactory;
    private final IgnoreAuditFieldsOnDirtyCheckFlushEntityEventListener ignoreAuditFieldsOnDirtyCheckFlushEntityEventListener;

    @PostConstruct
    protected void init() {
        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        ServiceRegistryImplementor serviceRegistry = sessionFactory.getServiceRegistry();
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.FLUSH_ENTITY).clearListeners();
        registry.getEventListenerGroup(EventType.FLUSH_ENTITY).appendListener(ignoreAuditFieldsOnDirtyCheckFlushEntityEventListener);
    }

    @Component
    public static class IgnoreAuditFieldsOnDirtyCheckFlushEntityEventListener extends DefaultFlushEntityEventListener {
        private static final List<String> IGNORE_DIRTY_CHECK_PROPERTIES = List.of(
                "createdBy",
                "createdDatetime",
                "lastModifiedBy",
                "lastModifiedDatetime"
        );

        @Override
        protected void dirtyCheck(final FlushEntityEvent event) throws HibernateException {
            super.dirtyCheck(event);
            removeIgnoredDirtyCheckProperties(event);
        }

        private void removeIgnoredDirtyCheckProperties(final FlushEntityEvent event) {
            String[] propertyNames = event.getEntityEntry().getPersister().getPropertyNames();
            int[] dirtyProperties = event.getDirtyProperties();
            if (dirtyProperties == null) return;

            var newDirtyProperties = new java.util.ArrayList<Integer>();
            Object[] propertyValues = event.getPropertyValues();

            for (int dirtyProperty : dirtyProperties) {
                if (!IGNORE_DIRTY_CHECK_PROPERTIES.contains(propertyNames[dirtyProperty])) { // property is not in ignore list
                    newDirtyProperties.add(dirtyProperty);
                } else if (propertyValues[dirtyProperty] != null) { // property is in ignore list but is not null
                    newDirtyProperties.add(dirtyProperty);
                }
            }
            int[] newDirtyPropertiesArray = newDirtyProperties.stream().mapToInt(i -> i).toArray();
            event.setDirtyProperties(newDirtyPropertiesArray.length > 0 ? newDirtyPropertiesArray : null);
        }

    }
}