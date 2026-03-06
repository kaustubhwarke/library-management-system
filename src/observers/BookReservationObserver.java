package observers;

import models.Book;
import models.Patron;

/**
 * Observer interface for book reservation notifications.
 * Demonstrates the Observer Pattern and Dependency Inversion Principle.
 */
public interface BookReservationObserver {
    /**
     * Called when a reserved book becomes available
     * @param book The book that became available
     * @param patron The patron who reserved the book
     */
    void onBookAvailable(Book book, Patron patron);
}

