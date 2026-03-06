package services;

import models.Book;
import models.Patron;
import models.Reservation;
import observers.BookReservationObserver;
import enums.ReservationStatus;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Manages book reservations with observer pattern for notifications.
 * Demonstrates Observer Pattern and Single Responsibility Principle.
 */
public class ReservationSystem {
    private static final Logger logger = Logger.getLogger(ReservationSystem.class.getName());

    private Map<String, Queue<Reservation>> reservations; // ISBN -> Queue of reservations
    private Map<String, Reservation> reservationById; // ReservationId -> Reservation
    private List<BookReservationObserver> observers;

    public ReservationSystem() {
        this.reservations = new HashMap<>();
        this.reservationById = new HashMap<>();
        this.observers = new ArrayList<>();
    }

    /**
     * Add an observer to be notified when books become available
     */
    public void addObserver(BookReservationObserver observer) {
        observers.add(observer);
        logger.log(Level.INFO, "Observer added to reservation system");
    }

    /**
     * Remove an observer
     */
    public void removeObserver(BookReservationObserver observer) {
        observers.remove(observer);
        logger.log(Level.INFO, "Observer removed from reservation system");
    }

    /**
     * Reserve a book for a patron
     */
    public boolean reserveBook(Patron patron, Book book) {
        String isbn = book.getIsbn();

        // Check if patron already has active reservation for this book
        Queue<Reservation> bookReservations = reservations.get(isbn);
        if (bookReservations != null) {
            for (Reservation reservation : bookReservations) {
                if (reservation.getPatron().getPatronId().equals(patron.getPatronId()) &&
                    reservation.getStatus() == ReservationStatus.ACTIVE) {
                    logger.log(Level.WARNING, String.format(
                        "Patron %s already has an active reservation for book '%s'",
                        patron.getName(), book.getTitle()
                    ));
                    return false;
                }
            }
        }

        // Create reservation
        Reservation reservation = new Reservation(patron, book);

        // Add to queue
        reservations.computeIfAbsent(isbn, k -> new LinkedList<>()).add(reservation);
        reservationById.put(reservation.getReservationId(), reservation);

        logger.log(Level.INFO, String.format(
            "Book reserved: '%s' for %s (Position in queue: %d)",
            book.getTitle(), patron.getName(), reservations.get(isbn).size()
        ));

        return true;
    }

    /**
     * Cancel a reservation
     */
    public boolean cancelReservation(String reservationId) {
        Reservation reservation = reservationById.get(reservationId);
        if (reservation == null) {
            logger.log(Level.WARNING, String.format(
                "Reservation not found: ID %s", reservationId
            ));
            return false;
        }

        reservation.cancel();

        // Remove from queue
        String isbn = reservation.getBook().getIsbn();
        Queue<Reservation> queue = reservations.get(isbn);
        if (queue != null) {
            queue.remove(reservation);
        }

        logger.log(Level.INFO, String.format(
            "Reservation cancelled: '%s' for %s",
            reservation.getBook().getTitle(), reservation.getPatron().getName()
        ));

        return true;
    }

    /**
     * Notify the next patron in queue when a book becomes available
     */
    public void notifyNextPatron(Book book) {
        String isbn = book.getIsbn();
        Queue<Reservation> queue = reservations.get(isbn);

        if (queue == null || queue.isEmpty()) {
            return; // No reservations for this book
        }

        // Get next active reservation
        Reservation nextReservation = queue.poll();
        while (nextReservation != null && nextReservation.getStatus() != ReservationStatus.ACTIVE) {
            nextReservation = queue.poll();
        }

        if (nextReservation == null) {
            return; // No active reservations
        }

        // Mark reservation as fulfilled
        nextReservation.markFulfilled();

        // Notify all observers
        for (BookReservationObserver observer : observers) {
            observer.onBookAvailable(book, nextReservation.getPatron());
        }

        logger.log(Level.INFO, String.format(
            "Next patron notified: %s for book '%s'",
            nextReservation.getPatron().getName(), book.getTitle()
        ));
    }

    /**
     * Get all reservations for a book
     */
    public List<Reservation> getBookReservations(String isbn) {
        Queue<Reservation> queue = reservations.get(isbn);
        if (queue == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(queue);
    }

    /**
     * Get all reservations by a patron
     */
    public List<Reservation> getPatronReservations(String patronId) {
        List<Reservation> patronReservations = new ArrayList<>();
        for (Reservation reservation : reservationById.values()) {
            if (reservation.getPatron().getPatronId().equals(patronId) &&
                reservation.getStatus() == ReservationStatus.ACTIVE) {
                patronReservations.add(reservation);
            }
        }
        return patronReservations;
    }

    /**
     * Get reservation count for a book
     */
    public int getReservationCount(String isbn) {
        Queue<Reservation> queue = reservations.get(isbn);
        if (queue == null) {
            return 0;
        }
        return (int) queue.stream()
                .filter(r -> r.getStatus() == ReservationStatus.ACTIVE)
                .count();
    }

    /**
     * Check if a book has reservations
     */
    public boolean hasReservations(String isbn) {
        return getReservationCount(isbn) > 0;
    }
}

