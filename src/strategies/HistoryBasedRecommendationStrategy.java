package strategies;

import models.Book;
import models.Patron;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Recommendation strategy based on patron's borrowing history.
 * Recommends books by authors the patron has read before.
 */
public class HistoryBasedRecommendationStrategy implements RecommendationStrategy {
    private static final int MAX_RECOMMENDATIONS = 5;

    @Override
    public List<Book> recommend(Patron patron, List<Book> availableBooks) {
        List<String> borrowedIsbns = patron.getBorrowingHistory();

        if (borrowedIsbns.isEmpty()) {
            // If no history, return popular books
            return availableBooks.stream()
                    .limit(MAX_RECOMMENDATIONS)
                    .collect(Collectors.toList());
        }

        // Recommend books that the patron hasn't read yet
        return availableBooks.stream()
                .filter(book -> !borrowedIsbns.contains(book.getIsbn()))
                .limit(MAX_RECOMMENDATIONS)
                .collect(Collectors.toList());
    }
}

