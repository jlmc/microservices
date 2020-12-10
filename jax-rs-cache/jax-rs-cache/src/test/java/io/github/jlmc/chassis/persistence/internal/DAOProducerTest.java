package io.github.jlmc.chassis.persistence.internal;

import io.github.jlmc.chassis.persistence.EntityMetadata;
import io.github.jlmc.service.books.entity.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DAOProducerTest {

    @Test
    void should_create_a_new_dao_instance() {
        EntityMetadata<Book, Integer> metadata = EntityMetadata.of(Book.class);

        Assertions.assertNotNull(metadata.getEntityType());
        Assertions.assertNotNull(metadata.getEntityPkType());
        Assertions.assertNotNull(metadata.getIdentifierField());
        Assertions.assertEquals("id", metadata.getIdentifierFieldName());
    }
}