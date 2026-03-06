package models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * Represents a loan of a book to a patron.
 * Demonstrates encapsulation and business logic.
 */
public class Loan {
    private String loanId;
    private Book book;
    private Patron patron;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private static final int LOAN_PERIOD_DAYS = 14; // 2 weeks loan period

    /**
     * Constructor for creating a new loan
     */
    public Loan(Book book, Patron patron) {
        this.loanId = UUID.randomUUID().toString();
        this.book = book;
        this.patron = patron;
        this.checkoutDate = LocalDate.now();
        this.dueDate = checkoutDate.plusDays(LOAN_PERIOD_DAYS);
        this.returnDate = null;
    }

    /**
     * Mark the loan as returned
     */
    public void markReturned() {
        this.returnDate = LocalDate.now();
    }

    /**
     * Check if the loan is overdue
     */
    public boolean isOverdue() {
        if (returnDate != null) {
            return false; // Already returned
        }
        return LocalDate.now().isAfter(dueDate);
    }

    /**
     * Check if the book has been returned
     */
    public boolean isReturned() {
        return returnDate != null;
    }

    /**
     * Get the number of days overdue (0 if not overdue)
     */
    public long getDaysOverdue() {
        if (!isOverdue()) {
            return 0;
        }
        return ChronoUnit.DAYS.between(dueDate, LocalDate.now());
    }

    // Getters
    public String getLoanId() {
        return loanId;
    }

    public Book getBook() {
        return book;
    }

    public Patron getPatron() {
        return patron;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public static int getLoanPeriodDays() {
        return LOAN_PERIOD_DAYS;
    }

    @Override
    public String toString() {
        String status = isReturned() ? "Returned on " + returnDate :
                       (isOverdue() ? "OVERDUE by " + getDaysOverdue() + " days" : "Active");
        return String.format("Loan{ID='%s', Book='%s', Patron='%s', Due=%s, Status=%s}",
                loanId, book.getTitle(), patron.getName(), dueDate, status);
    }
}

