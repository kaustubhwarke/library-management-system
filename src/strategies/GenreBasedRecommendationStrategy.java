package strategies;

import enums.BookGenre;
import models.Book;
import models.Patron;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Recommendation strategy based on patron's favorite genre.
 * Recommends books from genres the patron prefers.
 */
public class GenreBasedRecommendationStrategy implements RecommendationStrategy {
    private static final int MAX_RECOMMENDATIONS = 5;

    @Override
    public List<Book> recommend(Patron patron, List<Book> availableBooks) {
        BookGenre favoriteGenre = patron.getFavoriteGenre();
        List<String> borrowedIsbns = patron.getBorrowingHistory();

        if (favoriteGenre == null) {
            // If no genre preference, return first available books
            return availableBooks.stream()
                    .filter(book -> !borrowedIsbns.contains(book.getIsbn()))
                    .limit(MAX_RECOMMENDATIONS)
                    .collect(Collectors.toList());
        }

        // Recommend books from favorite genre that patron hasn't read
        return availableBooks.stream()
                .filter(book -> book.getGenre() == favoriteGenre)
                .filter(book -> !borrowedIsbns.contains(book.getIsbn()))
                .limit(MAX_RECOMMENDATIONS)
                .collect(Collectors.toList());
    }
}

