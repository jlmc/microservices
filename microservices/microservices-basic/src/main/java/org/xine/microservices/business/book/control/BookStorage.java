package org.xine.microservices.business.book.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

import org.xine.microservices.business.book.entity.Book;

@Singleton
public class BookStorage {

	private final Map<Long, Book> books = new HashMap<>();

	private final AtomicLong count = new AtomicLong(0);

	@PostConstruct
	public void startUp() {

		final Book javaEEPattern =
				Book.Builder.init()
				.withId(1L)
				.withAuthor("Adam Bien")
				.withDescription("Rethinking Best Practices")
				.withIsbn("1234")
				.withNumberOfPages(432)
				.withPrice(40.0D)
				.withTitle("Java EE Pattern")
				.build();

		final Book javaEE7Essentials = 
				Book.Builder.init()
				.withId(2L)
				.withAuthor("Arun Gupta")
				.withDescription("Enterprise Developer Handbook")
				.withIsbn("2345")
				.withNumberOfPages(362)
				.withPrice(24.0D)
				.withTitle("Java EE 7 Essentials")
				.build();

		this.books.put(javaEEPattern.getId(), javaEEPattern);
		this.books.put(javaEE7Essentials.getId(), javaEE7Essentials);

		this.count.set(this.books.values().size());
	}

	public List<Book> search() {
		return new ArrayList<Book>(this.books.values());
	}

	public Book search(Long id) {
		return this.books.get(id);
	}

	public Book save(Book book) {
		if (book.getId() != null) {
			this.books.put(book.getId(), book);
		} else {
			book.setId(this.count.incrementAndGet());
			this.books.put(book.getId(), book);
		}

		return book;
	}

}
