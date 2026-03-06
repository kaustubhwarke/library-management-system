package factory;

import enums.BookGenre;
import models.Book;
import models.Patron;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Concrete factory for creating standard library items.
 * Demonstrates the Factory Pattern implementation.
 */
public class StandardLibraryItemFactory extends LibraryItemFactory {
    private static final Logger logger = Logger.getLogger(StandardLibraryItemFactory.class.getName());

    @Override
    public Book createBook(String isbn, String title, String author,
                          int publicationYear, BookGenre genre, int totalCopies) {
        logger.log(Level.INFO, String.format(
            "Creating book: %s by %s (ISBN: %s)", title, author, isbn
        ));
        return new Book(isbn, title, author, publicationYear, genre, totalCopies);
    }

    @Override
    public Book createBook(String isbn, String title, String author,
                          int publicationYear, BookGenre genre,
                          int totalCopies, String description) {
        logger.log(Level.INFO, String.format(
            "Creating book with description: %s by %s (ISBN: %s)", title, author, isbn
        ));
        return new Book(isbn, title, author, publicationYear, genre, totalCopies, description);
    }

    @Override
    public Patron createPatron(String patronId, String name, String email, String phoneNumber) {
        // Generate patron ID if not provided
        if (patronId == null || patronId.isEmpty()) {
            patronId = "P-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }

        logger.log(Level.INFO, String.format(
            "Creating patron: %s (ID: %s)", name, patronId
        ));
        return new Patron(patronId, name, email, phoneNumber);
    }

    /**
     * Convenience method to create patron with auto-generated ID
     */
    public Patron createPatron(String name, String email, String phoneNumber) {
        return createPatron(null, name, email, phoneNumber);
    }
}

