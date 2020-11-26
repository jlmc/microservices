package io.github.jlmc.bookshelf.core.jsonb;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

/**
 * <h2>Performance Tip</h2>
 *
 * The Java documentation advised that for optimal use, instances of JsonbBuilder and Jsonb
 * should be reused and that for a typical use-case, only one Jsonb instance is required per application.
 * This is possible because its methods are thread safe.
 */
@ApplicationScoped
public class JsonbExposer {

    @Produces
    @ApplicationScoped
    public Jsonb expose() {
        return JsonbBuilder.create();
    }
}
