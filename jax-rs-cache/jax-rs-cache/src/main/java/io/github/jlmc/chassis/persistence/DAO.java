package io.github.jlmc.chassis.persistence;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Objects;
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

        CriteriaQuery<E> query = builder.createQuery(entityType());
        Root<E> root = query.from(entityType());
        query.orderBy(builder.desc(root.get(identifierFieldName())));

        return em.createQuery(query).getResultList();
    }

    public E saveAndFlush(E entity) {
        Objects.requireNonNull(entity);
        em.persist(entity);
        em.flush();
        return entity;
    }

    public E refresh(E entity) {
        Objects.requireNonNull(entity);
        em.refresh(entity);
        return entity;
    }

    public void flush() {
        em.flush();
    }

    public E getById(PK id) {
        return em.find(entityType(), id);
    }

    public Optional<E> findById(PK id) {
        return Optional.ofNullable(getById(id));
    }

    public E findByIdOrElseThrow(PK id) {
        return findById(id)
                .orElseThrow(() -> new NotFoundException("No <" + entityType().getName() + "> found with the identifier <" + id + ">"));
    }

    public boolean exists(PK id) {
        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<Boolean> query = builder.createQuery(Boolean.class);
        Root<E> root = query.from(entityType());

        query.select(builder.literal(Boolean.TRUE))
                .where(builder.equal(root.get(identifierFieldName()), id));

        try {
            return em.createQuery(query)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            return false;
        }
    }

    private String identifierFieldName() {
        return metadata.getIdentifierFieldName();
    }

    public Optional<E> findBy(Specification<E> spec) {
        CriteriaQuery<E> query = createSpecificationQuery(spec, entityType());

        try {
            E singleResult = em.createQuery(query).getSingleResult();
            return Optional.of(singleResult);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    private Class<E> entityType() {
        return metadata.getEntityType();
    }

    List<E> findAll(Specification<E> spec) {
        CriteriaQuery<E> query = createSpecificationQuery(spec, metadata.getEntityType());

       return em.createQuery(query).getResultList();
    }

    private <T> CriteriaQuery<T> createSpecificationQuery(Specification<E> spec, Class<T> resultClass) {
        Objects.requireNonNull(spec);
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(resultClass);
        Root<E> root = query.from(entityType());
        query.where(spec.toPredicate(root, query, builder));
        return query;
    }

    public long count(Specification<E> spec) {
        Objects.requireNonNull(spec);
        CriteriaQuery<Long> query = createSpecificationQuery(spec, Long.class);

        return em.createQuery(query).getSingleResult();
    }

    /*
    Page<T> findAll(@Nullable Specification<T> spec, Pageable pageable);
    List<T> findAll(@Nullable Specification<T> spec, Sort sort);
    */

}
