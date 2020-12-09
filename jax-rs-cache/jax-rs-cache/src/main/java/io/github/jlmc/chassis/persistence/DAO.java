package io.github.jlmc.chassis.persistence;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.List;
import java.util.Optional;

public class DAO<E, PK> {

    private final EntityManager em;
    private final EntityMetadata<E, PK> metadata;

    public DAO(EntityManager em, EntityMetadata<E, PK> metadata) {
        this.em = em;
        this.metadata = metadata;
    }

    public List<E> all() {
        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<E> query = builder.createQuery(metadata.getEntityType());
        Root<E> root = query.from(metadata.getEntityType());
        query.orderBy(builder.desc(root.get(metadata.getIdentifierFieldName())));

        return em.createQuery(query).getResultList();
    }

    public E saveAndFlush(E entity) {
        em.persist(entity);
        em.flush();
        return entity;
    }

    public E getById(PK id) {
        return em.find(metadata.getEntityType(), id);
    }

    public Optional<E> findById(PK id) {
        return Optional.ofNullable(getById(id));
    }

    public boolean exist(PK id) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Boolean> query = builder.createQuery(Boolean.class);
        query.from(metadata.getEntityType());

        Subquery<Integer> subQuery = query.subquery(Integer.class);
        Root<E> from = subQuery.from(metadata.getEntityType());
        subQuery.select(builder.literal(1)).where(builder.equal(from.get(metadata.getIdentifierFieldName()), id));

        query.select(builder.exists(subQuery));

        return em.createQuery(query).getSingleResult();
    }

}
