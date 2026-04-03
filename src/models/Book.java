package models;

import enums.BookGenre;

/**
 * Represents a book in the library system.
 * Demonstrates encapsulation with private fields and public accessors.
 */
public class Book {
    private String isbn;
    private String title;
    private String author;
    private int publicationYear;
    private BookGenre genre;
    private int totalCopies;
    private String description;

    /**
     * Constructor for creating a Book object
     */
    public Book(String isbn, String title, String author, int publicationYear, BookGenre genre, int totalCopies) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.genre = genre;
        this.totalCopies = totalCopies;
        this.description = "";
    }

    /**
     * Overloaded constructor with description
     */
    public Book(String isbn, String title, String author, int publicationYear, BookGenre genre, int totalCopies, String description) {
        this(isbn, title, author, publicationYear, genre, totalCopies);
        this.description = description;
    }

    // Getters
    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public BookGenre getGenre() {
        return genre;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setGenre(BookGenre genre) {
        this.genre = genre;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("Book{ISBN='%s', Title='%s', Author='%s', Year=%d, Genre=%s, Copies=%d}",
                isbn, title, author, publicationYear, genre, totalCopies);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
}

