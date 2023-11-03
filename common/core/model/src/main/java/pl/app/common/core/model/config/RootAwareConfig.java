package pl.app.common.core.model.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import org.hibernate.CallbackException;
import org.hibernate.Interceptor;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.AuditorAware;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.core.model.Audit;
import pl.app.common.core.model.RootAware;

import java.io.Serializable;
import java.time.Instant;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@Import(TransactionConfig.class)
public class RootAwareConfig {
    @Bean
    @Scope(scopeName = "transaction")
    public RootAwareService rootAwareService(EntityManager entityManager, AuditorAware<?> auditorAware) {
        return new RootAwareService(entityManager, auditorAware);
    }

    @Bean
    public Interceptor hibernateInterceptor(@Qualifier("rootAwareService") ObjectFactory<RootAwareService> rootAwareServiceObjectFactory) {
        return new RootAwareInterceptor(rootAwareServiceObjectFactory);
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(Interceptor hibernateInterceptor) {
        return hibernateProperties -> {
            hibernateProperties.put("hibernate.session_factory.interceptor", hibernateInterceptor);
        };
    }

    @Transactional
    public static class RootAwareService {
        private final Logger LOGGER = LoggerFactory.getLogger(RootAwareService.class);
        private final Set<Object> rootEntitiesToUpdate = ConcurrentHashMap.newKeySet();
        private final EntityManager entityManager;
        private final AuditorAware<?> auditorAware;

        public RootAwareService(EntityManager entityManager, AuditorAware<?> auditorAware) {
            this.entityManager = entityManager;
            this.auditorAware = auditorAware;
        }

        public void awareEntityWasUpdated(Object object) {
            if (object instanceof RootAware<?> rootAware) {
                addEntityToUpdate(rootAware.root());
            }
        }

        public void awareEntityWasDeleted(Object object) {
            if (object instanceof RootAware<?> rootAware) {
                addEntityToUpdate(rootAware.root());
            }
        }

        public void awareEntityWasCreated(Object object) {
            if (object instanceof RootAware<?> rootAware) {
                addEntityToUpdate(rootAware.root());
            }
        }

        private void addEntityToUpdate(Object object) {
            if (object != null) {
                rootEntitiesToUpdate.add(object);
                LOGGER.trace("Added root entity to update " + object.getClass());
            }
        }

        public void updateRootEntities() {
            if (!rootEntitiesToUpdate.isEmpty()) {
                LOGGER.debug("Updating root entities");
                rootEntitiesToUpdate.forEach(obj -> {
                    if (obj instanceof Audit) {
                        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
                        CriteriaUpdate<?> criteriaUpdate = cb.createCriteriaUpdate(obj.getClass());
                        criteriaUpdate.set("lastModifiedDate", Instant.now());
                        criteriaUpdate.set("lastModifiedBy", auditorAware.getCurrentAuditor().orElse(null));
                        this.entityManager.createQuery(criteriaUpdate).executeUpdate();
                        LOGGER.trace("Updated root entity " + obj.getClass());
                    }
                });
                rootEntitiesToUpdate.clear();
            }
        }
    }

    public static class RootAwareInterceptor implements Interceptor, Serializable {
        private final Logger LOGGER = LoggerFactory.getLogger(RootAwareInterceptor.class);
        private final ObjectFactory<RootAwareService> rootAwareServiceObjectFactory;

        public RootAwareInterceptor(ObjectFactory<RootAwareService> rootAwareServiceObjectFactory) {
            this.rootAwareServiceObjectFactory = rootAwareServiceObjectFactory;
        }

        @Override
        public void onDelete(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
            rootAwareServiceObjectFactory.getObject().awareEntityWasDeleted(entity);
            Interceptor.super.onDelete(entity, id, state, propertyNames, types);
        }

        @Override
        public boolean onFlushDirty(Object entity, Object id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) throws CallbackException {
            rootAwareServiceObjectFactory.getObject().awareEntityWasUpdated(entity);
            return Interceptor.super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
        }

        @Override
        public boolean onSave(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
            rootAwareServiceObjectFactory.getObject().awareEntityWasCreated(entity);
            return Interceptor.super.onSave(entity, id, state, propertyNames, types);
        }

        @Override
        public void postFlush(Iterator<Object> entities) throws CallbackException {
            rootAwareServiceObjectFactory.getObject().updateRootEntities();
            Interceptor.super.postFlush(entities);
        }
    }
}
