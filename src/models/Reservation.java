package models;

import enums.ReservationStatus;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents a reservation of a book by a patron.
 * Demonstrates encapsulation and state management.
 */
public class Reservation {
    private String reservationId;
    private Patron patron;
    private Book book;
    private LocalDate reservationDate;
    private ReservationStatus status;

    /**
     * Constructor for creating a new reservation
     */
    public Reservation(Patron patron, Book book) {
        this.reservationId = UUID.randomUUID().toString();
        this.patron = patron;
        this.book = book;
        this.reservationDate = LocalDate.now();
        this.status = ReservationStatus.ACTIVE;
    }

    /**
     * Mark the reservation as fulfilled
     */
    public void markFulfilled() {
        this.status = ReservationStatus.FULFILLED;
    }

    /**
     * Cancel the reservation
     */
    public void cancel() {
        this.status = ReservationStatus.CANCELLED;
    }

    /**
     * Mark the reservation as expired
     */
    public void markExpired() {
        this.status = ReservationStatus.EXPIRED;
    }

    // Getters
    public String getReservationId() {
        return reservationId;
    }

    public Patron getPatron() {
        return patron;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return String.format("Reservation{ID='%s', Book='%s', Patron='%s', Date=%s, Status=%s}",
                reservationId, book.getTitle(), patron.getName(), reservationDate, status);
    }
}

