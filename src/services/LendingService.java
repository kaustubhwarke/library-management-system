package services;

import models.Book;
import models.Loan;
import models.Patron;
import java.util.*;
import java.util.stream.Collectors;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Manages book lending operations.
 * Demonstrates Single Responsibility Principle.
 */
public class LendingService {
    private static final Logger logger = Logger.getLogger(LendingService.class.getName());

    private List<Loan> loans;
    private Map<String, List<Loan>> patronLoans; // PatronId -> List of active loans
    private Inventory inventory;
    private PatronManager patronManager;

    public LendingService(Inventory inventory, PatronManager patronManager) {
        this.loans = new ArrayList<>();
        this.patronLoans = new HashMap<>();
        this.inventory = inventory;
        this.patronManager = patronManager;
    }

    /**
     * Checkout a book to a patron
     */
    public boolean checkoutBook(String patronId, String isbn) {
        // Validate patron
        Patron patron = patronManager.getPatron(patronId);
        if (patron == null) {
            logger.log(Level.WARNING, String.format(
                "Checkout failed: Patron not found (ID: %s)", patronId
            ));
            return false;
        }

        // Validate book
        Book book = inventory.getBook(isbn);
        if (book == null) {
            logger.log(Level.WARNING, String.format(
                "Checkout failed: Book not found (ISBN: %s)", isbn
            ));
            return false;
        }

        // Check availability
        if (!inventory.isAvailable(isbn)) {
            logger.log(Level.WARNING, String.format(
                "Checkout failed: Book not available (ISBN: %s)", isbn
            ));
            return false;
        }

        // Create loan
        Loan loan = new Loan(book, patron);
        loans.add(loan);

        // Track patron loans
        patronLoans.computeIfAbsent(patronId, k -> new ArrayList<>()).add(loan);

        // Update inventory
        inventory.markAsBorrowed(isbn);

        // Update patron history
        patron.addToHistory(isbn, book.getGenre());

        logger.log(Level.INFO, String.format(
            "Book checked out: '%s' to %s (Due: %s)",
            book.getTitle(), patron.getName(), loan.getDueDate()
        ));

        return true;
    }

    /**
     * Return a book
     */
    public boolean returnBook(String patronId, String isbn) {
        // Find active loan
        Loan loan = findActiveLoan(patronId, isbn);
        if (loan == null) {
            logger.log(Level.WARNING, String.format(
                "Return failed: No active loan found for patron %s and book ISBN %s",
                patronId, isbn
            ));
            return false;
        }

        // Mark loan as returned
        loan.markReturned();

        // Update inventory
        inventory.markAsReturned(isbn);

        // Remove from patron's active loans
        List<Loan> activeLoans = patronLoans.get(patronId);
        if (activeLoans != null) {
            activeLoans.remove(loan);
        }

        String overdueInfo = loan.getDaysOverdue() > 0 ?
            String.format(" (OVERDUE by %d days)", loan.getDaysOverdue()) : "";

        logger.log(Level.INFO, String.format(
            "Book returned: '%s' by %s%s",
            loan.getBook().getTitle(), loan.getPatron().getName(), overdueInfo
        ));

        return true;
    }

    /**
     * Find an active loan for a patron and book
     */
    private Loan findActiveLoan(String patronId, String isbn) {
        List<Loan> activeLoans = patronLoans.get(patronId);
        if (activeLoans == null) {
            return null;
        }

        return activeLoans.stream()
                .filter(loan -> loan.getBook().getIsbn().equals(isbn))
                .filter(loan -> !loan.isReturned())
                .findFirst()
                .orElse(null);
    }

    /**
     * Get all active loans for a patron
     */
    public List<Loan> getPatronActiveLoans(String patronId) {
        List<Loan> activeLoans = patronLoans.get(patronId);
        if (activeLoans == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(activeLoans);
    }

    /**
     * Get all loans (active and returned)
     */
    public List<Loan> getAllLoans() {
        return new ArrayList<>(loans);
    }

    /**
     * Get all overdue loans
     */
    public List<Loan> getOverdueLoans() {
        return loans.stream()
                .filter(loan -> !loan.isReturned() && loan.isOverdue())
                .collect(Collectors.toList());
    }

    /**
     * Get loan history for a patron
     */
    public List<Loan> getPatronLoanHistory(String patronId) {
        return loans.stream()
                .filter(loan -> loan.getPatron().getPatronId().equals(patronId))
                .collect(Collectors.toList());
    }

    /**
     * Check if a patron has an active loan for a book
     */
    public boolean hasActiveLoan(String patronId, String isbn) {
        return findActiveLoan(patronId, isbn) != null;
    }
}

