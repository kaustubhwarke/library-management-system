package strategies;

import models.Book;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete strategy for searching books by title.
 * Demonstrates Strategy Pattern implementation.
 */
public class SearchByTitleStrategy implements SearchStrategy {
    private String title;

    public SearchByTitleStrategy(String title) {
        this.title = title.toLowerCase();
    }

    @Override
    public List<Book> search(Collection<Book> books) {
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title))
                .collect(Collectors.toList());
    }
}

