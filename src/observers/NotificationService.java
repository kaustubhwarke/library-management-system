package observers;

import models.Book;
import models.Patron;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Concrete observer that sends notifications when books become available.
 * Demonstrates the Observer Pattern implementation.
 */
public class NotificationService implements BookReservationObserver {
    private static final Logger logger = Logger.getLogger(NotificationService.class.getName());

    @Override
    public void onBookAvailable(Book book, Patron patron) {
        // Simulate sending notification
        sendEmailNotification(patron, book);
        sendSMSNotification(patron, book);
        logger.log(Level.INFO, String.format(
            "Notification sent to %s (%s) - Book '%s' is now available",
            patron.getName(), patron.getEmail(), book.getTitle()
        ));
    }

    /**
     * Send email notification to patron
     */
    private void sendEmailNotification(Patron patron, Book book) {
        // Simulate email notification
        String message = String.format(
            "Dear %s,\n\nThe book '%s' by %s that you reserved is now available for checkout.\n\n" +
            "Please visit the library to check it out within 3 days, or your reservation will be cancelled.\n\n" +
            "Best regards,\nLibrary Management System",
            patron.getName(), book.getTitle(), book.getAuthor()
        );

        logger.log(Level.FINE, String.format("Email sent to %s: %s", patron.getEmail(), message));
    }

    /**
     * Send SMS notification to patron
     */
    private void sendSMSNotification(Patron patron, Book book) {
        // Simulate SMS notification
        String message = String.format(
            "Library: Your reserved book '%s' is now available. Check it out within 3 days.",
            book.getTitle()
        );

        logger.log(Level.FINE, String.format("SMS sent to %s: %s", patron.getPhoneNumber(), message));
    }
}

