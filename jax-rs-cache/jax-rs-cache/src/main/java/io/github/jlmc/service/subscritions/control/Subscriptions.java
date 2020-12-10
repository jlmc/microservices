package io.github.jlmc.service.subscritions.control;

import io.github.jlmc.chassis.persistence.DAO;
import io.github.jlmc.service.books.entity.Book;
import io.github.jlmc.service.subscritions.entity.Subscription;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

@Stateless
public class Subscriptions {

    @Inject
    DAO<Subscription, Integer> subscriptionDao;

    @Inject
    DAO<Book, Integer> bookDao;

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Subscription findById(Integer id) {
        return subscriptionDao.findBy(SubscriptionsSpecifications.findByIdFetchingTheBook(id))
                              .orElseThrow(() -> new NotFoundException("No <" + Subscription.class.getName() + "> found with the identifier <" + id + ">"));
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Subscription create(Subscription subscription) {
        Book book = bookDao.getById(subscription.getBook().getId());

        if (book == null) {
            throw new BadRequestException("The Book with the id <" + subscription.getBook().getId() + "> is invalid, it not exists in the system!");
        }

        return subscriptionDao.saveAndFlush(Subscription.of(subscription.getReader(), book));
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void approve(Integer id) {
        Subscription subscription = subscriptionDao.findByIdOrElseThrow(id);

        subscription.approve();
    }

    public boolean isApproved(Integer id) {
        Subscription subscription = subscriptionDao.findByIdOrElseThrow(id);
        return subscription.isApproved();
    }
}
