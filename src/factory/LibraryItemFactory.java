package factory;

import enums.BookGenre;
import models.Book;
import models.Patron;

/**
 * Abstract factory for creating library items.
 * Demonstrates the Factory Pattern and Open/Closed Principle.
 */
public abstract class LibraryItemFactory {

    /**
     * Create a book with the specified attributes
     */
    public abstract Book createBook(String isbn, String title, String author,
                                   int publicationYear, BookGenre genre, int totalCopies);

    /**
     * Create a book with description
     */
    public abstract Book createBook(String isbn, String title, String author,
                                   int publicationYear, BookGenre genre,
                                   int totalCopies, String description);

    /**
     * Create a patron with the specified attributes
     */
    public abstract Patron createPatron(String patronId, String name,
                                       String email, String phoneNumber);
}

