package io.github.jlmc.bookshelf.domain.services;

import io.github.jlmc.bookshelf.domain.entities.Book;
import io.github.jlmc.bookshelf.domain.entities.Loan;
import org.hibernate.Session;
import org.hibernate.annotations.QueryHints;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class Library {

    @PersistenceContext
    EntityManager em;

    /**
     * Return a book with given ISBN on given load.
     *
     * @param isbn   the ISBN
     * @param loanId the loan ID
     */
    public void returnBook(String isbn, String loanId) {
        Book book = getBooKReferenceByIsbn(isbn);
        book.removeLoan(Loan.withTheId(loanId));
    }

    private Book getBooKReferenceByIsbn(String isbn) {
        Book reference = em.unwrap(Session.class).bySimpleNaturalId(Book.class).getReference(isbn);
        return reference;
    }

    /**
     * Lend a book with given ISBN and create a new loan on it.
     *
     * @param isbn the book ISBN
     * @param loan the loan info
     */
    public void lendBook(String isbn, Loan loan) {
        Book book = getBooKReferenceByIsbn(isbn);
        book.addLoan(loan);
    }

    /**
     * Get the loan identified by its ID.
     *
     * @param loanId the loan ID
     * @return the loan
     */
    public Loan loanInfo(String loanId) {
        return em.find(Loan.class, loanId);
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Loan> allLoansByIsbn(String isbn) {
        return em.createQuery("""
                    select distinct l 
                    from Loan l left join fetch l.book b 
                    where b.isbn = :isbn 
                    order by l.start, l.end
                    """, Loan.class)
                 .setParameter("isbn", isbn)
                 .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                 .getResultList();
    }
}
