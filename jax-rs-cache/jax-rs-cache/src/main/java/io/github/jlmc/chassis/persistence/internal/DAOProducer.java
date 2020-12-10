package io.github.jlmc.chassis.persistence.internal;

import io.github.jlmc.chassis.persistence.DAO;
import io.github.jlmc.chassis.persistence.EntityMetadata;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;

public class DAOProducer {

    @PersistenceContext
    EntityManager entityManager;

    @Produces
    @Dependent
    public <E, PK> DAO<E, PK> createDaoInstance(InjectionPoint ip) {
        ParameterizedType parameterizedType = (ParameterizedType) ip.getType();

        //noinspection unchecked
        Class<E> type = (Class<E>) parameterizedType.getActualTypeArguments()[0];
        //Class<PK> typePK = (Class<PK>) parameterizedType.getActualTypeArguments()[1];

        EntityMetadata<E, PK> metadata = EntityMetadata.of(type);


        return new DAO<E, PK>(entityManager, metadata);
    }

}
