package strategies;

import models.Book;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete strategy for searching books by author.
 * Demonstrates Strategy Pattern implementation.
 */
public class SearchByAuthorStrategy implements SearchStrategy {
    private String author;

    public SearchByAuthorStrategy(String author) {
        this.author = author.toLowerCase();
    }

    @Override
    public List<Book> search(Collection<Book> books) {
        return books.stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(author))
                .collect(Collectors.toList());
    }
}

