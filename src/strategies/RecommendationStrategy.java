package strategies;

import models.Book;
import models.Patron;
import java.util.List;

/**
 * Strategy interface for different book recommendation algorithms.
 * Demonstrates the Strategy Pattern and Open/Closed Principle.
 */
public interface RecommendationStrategy {
    /**
     * Generate book recommendations for a patron
     * @param patron The patron to generate recommendations for
     * @param availableBooks List of books available for recommendation
     * @return List of recommended books
     */
    List<Book> recommend(Patron patron, List<Book> availableBooks);
}

