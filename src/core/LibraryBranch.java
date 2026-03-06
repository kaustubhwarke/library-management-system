package core;

import models.Book;
import models.Patron;
import services.*;
import strategies.SearchStrategy;
import observers.NotificationService;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Represents a single library branch.
 * Demonstrates composition and delegation of responsibilities.
 */
public class LibraryBranch {
    private static final Logger logger = Logger.getLogger(LibraryBranch.class.getName());

    private String branchId;
    private String branchName;
    private String location;
    private Inventory inventory;
    private PatronManager patronManager;
    private LendingService lendingService;
    private ReservationSystem reservationSystem;
    private RecommendationEngine recommendationEngine;

    /**
     * Constructor for creating a library branch
     */
    public LibraryBranch(String branchId, String branchName, String location) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.location = location;
        this.inventory = new Inventory();
        this.patronManager = new PatronManager();
        this.lendingService = new LendingService(inventory, patronManager);
        this.reservationSystem = new ReservationSystem();
        this.recommendationEngine = new RecommendationEngine();

        // Add notification observer to reservation system
        reservationSystem.addObserver(new NotificationService());

        logger.log(Level.INFO, String.format(
            "Library branch created: %s (%s) at %s",
            branchName, branchId, location
        ));
    }

    /**
     * Add a book to the branch inventory
     */
    public void addBook(Book book) {
        inventory.addBook(book);
    }

    /**
     * Remove a book from the branch inventory
     */
    public boolean removeBook(String isbn) {
        return inventory.removeBook(isbn);
    }

    /**
     * Update book information
     */
    public boolean updateBook(Book book) {
        return inventory.updateBook(book);
    }

    /**
     * Search for books using a specific strategy
     */
    public List<Book> searchBooks(SearchStrategy strategy) {
        return strategy.search(inventory.getAllBooks());
    }

    /**
     * Register a new patron
     */
    public void registerPatron(Patron patron) {
        patronManager.addPatron(patron);
    }

    /**
     * Update patron information
     */
    public boolean updatePatron(Patron patron) {
        return patronManager.updatePatron(patron);
    }

    /**
     * Checkout a book to a patron
     */
    public boolean checkoutBook(String patronId, String isbn) {
        boolean success = lendingService.checkoutBook(patronId, isbn);

        // If checkout successful and book has reservations, notify next patron
        if (success && reservationSystem.hasReservations(isbn)) {
            Book book = inventory.getBook(isbn);
            if (book != null && inventory.isAvailable(isbn)) {
                reservationSystem.notifyNextPatron(book);
            }
        }

        return success;
    }

    /**
     * Return a book
     */
    public boolean returnBook(String patronId, String isbn) {
        boolean success = lendingService.returnBook(patronId, isbn);

        // If return successful, notify next patron if there are reservations
        if (success && reservationSystem.hasReservations(isbn)) {
            Book book = inventory.getBook(isbn);
            if (book != null) {
                reservationSystem.notifyNextPatron(book);
            }
        }

        return success;
    }

    /**
     * Reserve a book for a patron
     */
    public boolean reserveBook(String patronId, String isbn) {
        Patron patron = patronManager.getPatron(patronId);
        Book book = inventory.getBook(isbn);

        if (patron == null || book == null) {
            return false;
        }

        return reservationSystem.reserveBook(patron, book);
    }

    /**
     * Transfer a book to another branch
     */
    public boolean transferBook(String isbn, LibraryBranch targetBranch) {
        Book book = inventory.getBook(isbn);
        if (book == null) {
            logger.log(Level.WARNING, String.format(
                "Cannot transfer book: ISBN %s not found in branch %s",
                isbn, branchName
            ));
            return false;
        }

        // Check if book is available (not borrowed)
        if (!inventory.isAvailable(isbn)) {
            logger.log(Level.WARNING, String.format(
                "Cannot transfer book: ISBN %s is currently borrowed",
                isbn
            ));
            return false;
        }

        // Remove from this branch and add to target branch
        if (inventory.removeBook(isbn)) {
            targetBranch.addBook(book);
            logger.log(Level.INFO, String.format(
                "Book transferred: '%s' from %s to %s",
                book.getTitle(), branchName, targetBranch.getBranchName()
            ));
            return true;
        }

        return false;
    }

    /**
     * Get book recommendations for a patron
     */
    public List<Book> getRecommendationsForPatron(String patronId) {
        Patron patron = patronManager.getPatron(patronId);
        if (patron == null) {
            return List.of();
        }

        return recommendationEngine.getRecommendations(patron, inventory.getAvailableBooks());
    }

    // Getters
    public String getBranchId() {
        return branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public String getLocation() {
        return location;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public PatronManager getPatronManager() {
        return patronManager;
    }

    public LendingService getLendingService() {
        return lendingService;
    }

    public ReservationSystem getReservationSystem() {
        return reservationSystem;
    }

    public RecommendationEngine getRecommendationEngine() {
        return recommendationEngine;
    }

    @Override
    public String toString() {
        return String.format("LibraryBranch{ID='%s', Name='%s', Location='%s'}",
                branchId, branchName, location);
    }
}

