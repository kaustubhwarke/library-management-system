package strategies;

import models.Book;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete strategy for searching books by ISBN.
 * Demonstrates Strategy Pattern implementation.
 */
public class SearchByISBNStrategy implements SearchStrategy {
    private String isbn;

    public SearchByISBNStrategy(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public List<Book> search(Collection<Book> books) {
        return books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .collect(Collectors.toList());
    }
}

