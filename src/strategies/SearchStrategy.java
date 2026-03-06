package strategies;

import models.Book;
import java.util.Collection;
import java.util.List;

/**
 * Strategy interface for different book search algorithms.
 * Demonstrates the Strategy Pattern and Open/Closed Principle.
 */
public interface SearchStrategy {
    /**
     * Search for books based on the specific strategy implementation
     * @param books Collection of books to search through
     * @return List of books matching the search criteria
     */
    List<Book> search(Collection<Book> books);
}

