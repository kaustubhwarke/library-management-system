package enums;

/**
 * Enum representing the status of a book reservation
 */
public enum ReservationStatus {
    ACTIVE,
    FULFILLED,
    CANCELLED,
    EXPIRED;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}

