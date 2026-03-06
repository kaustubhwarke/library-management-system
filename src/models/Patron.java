package models;

import enums.BookGenre;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a library patron (member).
 * Demonstrates encapsulation and tracks borrowing history.
 */
public class Patron {
    private String patronId;
    private String name;
    private String email;
    private String phoneNumber;
    private List<String> borrowingHistory; // List of ISBNs
    private Map<BookGenre, Integer> genrePreferences; // Genre -> count of books borrowed
    private String address;

    /**
     * Constructor for creating a Patron object
     */
    public Patron(String patronId, String name, String email, String phoneNumber) {
        this.patronId = patronId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.borrowingHistory = new ArrayList<>();
        this.genrePreferences = new HashMap<>();
        this.address = "";
    }

    /**
     * Add a book to the patron's borrowing history
     */
    public void addToHistory(String isbn, BookGenre genre) {
        borrowingHistory.add(isbn);
        genrePreferences.put(genre, genrePreferences.getOrDefault(genre, 0) + 1);
    }

    /**
     * Get the patron's favorite genre based on borrowing history
     */
    public BookGenre getFavoriteGenre() {
        if (genrePreferences.isEmpty()) {
            return null;
        }
        return genrePreferences.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    // Getters
    public String getPatronId() {
        return patronId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<String> getBorrowingHistory() {
        return new ArrayList<>(borrowingHistory);
    }

    public Map<BookGenre, Integer> getGenrePreferences() {
        return new HashMap<>(genrePreferences);
    }

    public String getAddress() {
        return address;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return String.format("Patron{ID='%s', Name='%s', Email='%s', Phone='%s', BooksRead=%d}",
                patronId, name, email, phoneNumber, borrowingHistory.size());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Patron patron = (Patron) obj;
        return patronId.equals(patron.patronId);
    }

    @Override
    public int hashCode() {
        return patronId.hashCode();
    }
}

