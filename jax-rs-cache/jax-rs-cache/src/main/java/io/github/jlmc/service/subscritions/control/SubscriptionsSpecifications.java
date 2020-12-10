package io.github.jlmc.service.subscritions.control;

import io.github.jlmc.chassis.persistence.Specification;
import io.github.jlmc.service.subscritions.entity.Subscription;

import javax.persistence.criteria.JoinType;

public class SubscriptionsSpecifications {

    static Specification<Subscription> findByIdFetchingTheBook(Integer id) {
        return (root, query, builder) -> {
            if (query.getResultType() != Subscription.class) {
                root.join("book", JoinType.LEFT);
            } else {
                root.fetch("book", JoinType.LEFT);
            }

            return builder.equal(root.get("id"), id);
        };
    }
}
