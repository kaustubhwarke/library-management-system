package strategies;

import models.Book;
import models.Patron;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Recommendation strategy based on book popularity (total copies).
 * Recommends most popular books that patron hasn't read.
 */
public class PopularityBasedRecommendationStrategy implements RecommendationStrategy {
    private static final int MAX_RECOMMENDATIONS = 5;

    @Override
    public List<Book> recommend(Patron patron, List<Book> availableBooks) {
        List<String> borrowedIsbns = patron.getBorrowingHistory();

        // Recommend most popular books (highest total copies) that patron hasn't read
        return availableBooks.stream()
                .filter(book -> !borrowedIsbns.contains(book.getIsbn()))
                .sorted((b1, b2) -> Integer.compare(b2.getTotalCopies(), b1.getTotalCopies()))
                .limit(MAX_RECOMMENDATIONS)
                .collect(Collectors.toList());
    }
}

