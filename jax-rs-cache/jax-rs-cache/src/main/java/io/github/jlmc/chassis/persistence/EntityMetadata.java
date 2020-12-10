package io.github.jlmc.chassis.persistence;

import io.github.jlmc.chassis.reflection.Reflections;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.Objects;

public class EntityMetadata<E, PK> {
    private final Field identifierField;
    private final Class<E> entityType;
    private final Class<PK> entityPkType;

    private EntityMetadata(Class<E> entityType, Class<PK> entityPkType, Field identifierField) {
        this.entityType = entityType;
        this.entityPkType = entityPkType;
        this.identifierField = identifierField;
    }

    public static <E, PK> EntityMetadata<E, PK> of(Class<E> entityType) {
        Objects.requireNonNull(entityType);

        if (!entityType.isAnnotationPresent(Entity.class)) {
            throw new IllegalStateException(String.format("The Class <%s> is not annotated with <%s>!", entityType, Entity.class));
        }

        Field identifierField = Reflections.getSingleDeclaredFieldWithAnnotation(entityType, Id.class)
                .or(() -> Reflections.getSingleDeclaredFieldWithAnnotation(entityType, EmbeddedId.class))
                .orElseThrow(() -> new IllegalStateException(
                        String.format("The Class <%s> do not contain any field marked with <%s> or <%s> !",
                                entityType, Id.class, EmbeddedId.class)));


        //noinspection unchecked
        Class<PK> declaringClass = (Class<PK>) identifierField.getDeclaringClass();

        return new EntityMetadata<>(entityType, declaringClass, identifierField);
    }

    public Field getIdentifierField() {
        return identifierField;
    }

    public Class<E> getEntityType() {
        return entityType;
    }

    public Class<PK> getEntityPkType() {
        return entityPkType;
    }

    public String getIdentifierFieldName() {
        return identifierField.getName();
    }

}
