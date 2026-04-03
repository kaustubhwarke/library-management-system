package services;

import models.Book;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Manages the library's book inventory.
 * Demonstrates Single Responsibility Principle and encapsulation.
 */
public class Inventory {
    private static final Logger logger = Logger.getLogger(Inventory.class.getName());

    private Map<String, Book> books; // ISBN -> Book
    private Set<String> availableBooks; // ISBNs of available books
    private Map<String, Integer> borrowedCount; // ISBN -> count of borrowed copies

    public Inventory() {
        this.books = new HashMap<>();
        this.availableBooks = new HashSet<>();
        this.borrowedCount = new HashMap<>();
    }

    /**
     * Add a book to the inventory
     */
    public void addBook(Book book) {
        books.put(book.getIsbn(), book);
        availableBooks.add(book.getIsbn());
        borrowedCount.put(book.getIsbn(), 0);

        logger.log(Level.INFO, String.format(
            "Book added to inventory: %s (ISBN: %s, Copies: %d)",
            book.getTitle(), book.getIsbn(), book.getTotalCopies()
        ));
    }

    /**
     * Remove a book from the inventory
     */
    public boolean removeBook(String isbn) {
        if (!books.containsKey(isbn)) {
            logger.log(Level.WARNING, String.format("Book not found: ISBN %s", isbn));
            return false;
        }

        int borrowed = borrowedCount.getOrDefault(isbn, 0);
        if (borrowed > 0) {
            logger.log(Level.WARNING, String.format(
                "Cannot remove book: ISBN %s has %d copies currently borrowed", isbn, borrowed
            ));
            return false;
        }

        books.remove(isbn);
        availableBooks.remove(isbn);
        borrowedCount.remove(isbn);

        logger.log(Level.INFO, String.format("Book removed from inventory: ISBN %s", isbn));
        return true;
    }

    /**
     * Update book information
     */
    public boolean updateBook(Book updatedBook) {
        String isbn = updatedBook.getIsbn();
        if (!books.containsKey(isbn)) {
            logger.log(Level.WARNING, String.format("Book not found for update: ISBN %s", isbn));
            return false;
        }

        books.put(isbn, updatedBook);
        logger.log(Level.INFO, String.format("Book updated: ISBN %s", isbn));
        return true;
    }

    /**
     * Get a book by ISBN
     */
    public Book getBook(String isbn) {
        return books.get(isbn);
    }

    /**
     * Get all books in inventory
     */
    public Collection<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    /**
     * Check if a book is available for checkout
     */
    public boolean isAvailable(String isbn) {
        if (!books.containsKey(isbn)) {
            return false;
        }

        Book book = books.get(isbn);
        int borrowed = borrowedCount.getOrDefault(isbn, 0);
        return borrowed < book.getTotalCopies();
    }

    /**
     * Mark a book as borrowed
     */
    public boolean markAsBorrowed(String isbn) {
        if (!isAvailable(isbn)) {
            return false;
        }

        int borrowed = borrowedCount.getOrDefault(isbn, 0);
        borrowedCount.put(isbn, borrowed + 1);

        Book book = books.get(isbn);
        if (borrowed + 1 >= book.getTotalCopies()) {
            availableBooks.remove(isbn);
        }

        logger.log(Level.FINE, String.format(
            "Book marked as borrowed: ISBN %s (Borrowed: %d/%d)",
            isbn, borrowed + 1, book.getTotalCopies()
        ));
        return true;
    }

    /**
     * Mark a book as returned
     */
    public boolean markAsReturned(String isbn) {
        if (!books.containsKey(isbn)) {
            return false;
        }

        int borrowed = borrowedCount.getOrDefault(isbn, 0);
        if (borrowed <= 0) {
            logger.log(Level.WARNING, String.format(
                "Cannot mark as returned: ISBN %s has no borrowed copies", isbn
            ));
            return false;
        }

        borrowedCount.put(isbn, borrowed - 1);
        availableBooks.add(isbn);

        logger.log(Level.FINE, String.format(
            "Book marked as returned: ISBN %s (Borrowed: %d/%d)",
            isbn, borrowed - 1, books.get(isbn).getTotalCopies()
        ));
        return true;
    }

    /**
     * Get available book count
     */
    public int getAvailableCount(String isbn) {
        if (!books.containsKey(isbn)) {
            return 0;
        }

        Book book = books.get(isbn);
        int borrowed = borrowedCount.getOrDefault(isbn, 0);
        return book.getTotalCopies() - borrowed;
    }

    /**
     * Get all available books
     */
    public List<Book> getAvailableBooks() {
        List<Book> available = new ArrayList<>();
        for (String isbn : availableBooks) {
            if (isAvailable(isbn)) {
                available.add(books.get(isbn));
            }
        }
        return available;
    }
}

