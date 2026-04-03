import core.LibraryBranch;
import core.LibraryManagementSystem;
import enums.BookGenre;
import factory.StandardLibraryItemFactory;
import models.Book;
import models.Loan;
import models.Patron;
import strategies.*;

import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Main class demonstrating the Library Management System.
 * Showcases all core and advanced features.
 */
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        // Configure logging
        configureLogging();

        System.out.println("=".repeat(80));
        System.out.println("LIBRARY MANAGEMENT SYSTEM - COMPREHENSIVE DEMO");
        System.out.println("=".repeat(80));
        System.out.println();

        // Get singleton instance of the library management system
        LibraryManagementSystem lms = LibraryManagementSystem.getInstance();

        // Create library branches (Multi-branch Support)
        System.out.println(">>> CREATING LIBRARY BRANCHES");
        LibraryBranch mainBranch = new LibraryBranch("BR001", "Main Branch", "123 Main Street, Downtown");
        LibraryBranch westBranch = new LibraryBranch("BR002", "West Branch", "456 West Avenue, Westside");

        lms.addBranch(mainBranch);
        lms.addBranch(westBranch);
        System.out.println("✓ Created " + lms.getBranchCount() + " library branches\n");

        // Create factory for creating books and patrons (Factory Pattern)
        StandardLibraryItemFactory factory = new StandardLibraryItemFactory();

        // Add books to Main Branch (Book Management)
        System.out.println(">>> ADDING BOOKS TO MAIN BRANCH");
        Book book1 = factory.createBook(
            "978-0451524935", "1984", "George Orwell", 1949, BookGenre.FICTION, 3,
            "A dystopian social science fiction novel and cautionary tale."
        );
        Book book2 = factory.createBook(
            "978-0061120084", "To Kill a Mockingbird", "Harper Lee", 1960, BookGenre.FICTION, 2
        );
        Book book3 = factory.createBook(
            "978-0544003415", "The Lord of the Rings", "J.R.R. Tolkien", 1954, BookGenre.FANTASY, 4
        );
        Book book4 = factory.createBook(
            "978-0132350884", "Clean Code", "Robert C. Martin", 2008, BookGenre.TECHNOLOGY, 2
        );
        Book book5 = factory.createBook(
            "978-0307387899", "The Road", "Cormac McCarthy", 2006, BookGenre.FICTION, 2
        );

        mainBranch.addBook(book1);
        mainBranch.addBook(book2);
        mainBranch.addBook(book3);
        mainBranch.addBook(book4);
        mainBranch.addBook(book5);
        System.out.println("✓ Added 5 books to Main Branch\n");

        // Add books to West Branch
        System.out.println(">>> ADDING BOOKS TO WEST BRANCH");
        Book book6 = factory.createBook(
            "978-0316769174", "The Catcher in the Rye", "J.D. Salinger", 1951, BookGenre.FICTION, 3
        );
        Book book7 = factory.createBook(
            "978-0142437230", "Dune", "Frank Herbert", 1965, BookGenre.SCIENCE_FICTION, 2
        );

        westBranch.addBook(book6);
        westBranch.addBook(book7);
        System.out.println("✓ Added 2 books to West Branch\n");

        // Register patrons (Patron Management)
        System.out.println(">>> REGISTERING PATRONS");
        Patron patron1 = factory.createPatron("Alice Johnson", "alice@example.com", "555-0101");
        Patron patron2 = factory.createPatron("Bob Smith", "bob@example.com", "555-0102");
        Patron patron3 = factory.createPatron("Carol Davis", "carol@example.com", "555-0103");

        mainBranch.registerPatron(patron1);
        mainBranch.registerPatron(patron2);
        mainBranch.registerPatron(patron3);
        System.out.println("✓ Registered 3 patrons\n");

        // Search for books (Strategy Pattern - Search)
        System.out.println(">>> SEARCHING FOR BOOKS");
        System.out.println("\n--- Search by Author: 'Orwell' ---");
        SearchStrategy searchByAuthor = new SearchByAuthorStrategy("Orwell");
        List<Book> results = mainBranch.searchBooks(searchByAuthor);
        printSearchResults(results);

        System.out.println("\n--- Search by Title: 'Lord' ---");
        SearchStrategy searchByTitle = new SearchByTitleStrategy("Lord");
        results = mainBranch.searchBooks(searchByTitle);
        printSearchResults(results);

        System.out.println("\n--- Search by ISBN: '978-0061120084' ---");
        SearchStrategy searchByISBN = new SearchByISBNStrategy("978-0061120084");
        results = mainBranch.searchBooks(searchByISBN);
        printSearchResults(results);

        // Checkout books (Lending Process)
        System.out.println("\n>>> CHECKING OUT BOOKS");
        System.out.println("Alice checks out '1984':");
        boolean success = mainBranch.checkoutBook(patron1.getPatronId(), book1.getIsbn());
        System.out.println(success ? "✓ Checkout successful" : "✗ Checkout failed");

        System.out.println("\nAlice checks out 'To Kill a Mockingbird':");
        success = mainBranch.checkoutBook(patron1.getPatronId(), book2.getIsbn());
        System.out.println(success ? "✓ Checkout successful" : "✗ Checkout failed");

        System.out.println("\nBob checks out 'The Lord of the Rings':");
        success = mainBranch.checkoutBook(patron2.getPatronId(), book3.getIsbn());
        System.out.println(success ? "✓ Checkout successful" : "✗ Checkout failed");

        System.out.println("\nCarol checks out '1984':");
        success = mainBranch.checkoutBook(patron3.getPatronId(), book1.getIsbn());
        System.out.println(success ? "✓ Checkout successful" : "✗ Checkout failed");

        // Display active loans
        System.out.println("\n--- Active Loans ---");
        List<Loan> aliceLoans = mainBranch.getLendingService().getPatronActiveLoans(patron1.getPatronId());
        System.out.println("Alice's active loans: " + aliceLoans.size());
        aliceLoans.forEach(loan -> System.out.println("  - " + loan));

        // Reserve a book (Reservation System with Observer Pattern)
        System.out.println("\n>>> BOOK RESERVATION SYSTEM");
        System.out.println("Carol tries to reserve '1984' (already checked out):");
        success = mainBranch.reserveBook(patron3.getPatronId(), book1.getIsbn());
        System.out.println(success ? "✓ Reservation successful" : "✗ Reservation failed");

        System.out.println("\nBob also reserves '1984':");
        success = mainBranch.reserveBook(patron2.getPatronId(), book1.getIsbn());
        System.out.println(success ? "✓ Reservation successful" : "✗ Reservation failed");

        // Return a book (triggers notification to next person in reservation queue)
        System.out.println("\n>>> RETURNING BOOKS");
        System.out.println("Alice returns '1984':");
        success = mainBranch.returnBook(patron1.getPatronId(), book1.getIsbn());
        System.out.println(success ? "✓ Return successful (Notification sent to Carol)" : "✗ Return failed");

        System.out.println("\nAlice returns 'To Kill a Mockingbird':");
        success = mainBranch.returnBook(patron1.getPatronId(), book2.getIsbn());
        System.out.println(success ? "✓ Return successful" : "✗ Return failed");

        // Get recommendations (Strategy Pattern - Recommendations)
        System.out.println("\n>>> BOOK RECOMMENDATION SYSTEM");
        System.out.println("\n--- Recommendations for Alice (Genre-based) ---");
        List<Book> recommendations = mainBranch.getRecommendationsForPatron(patron1.getPatronId());
        printRecommendations(recommendations);

        System.out.println("\n--- Changing to History-based Strategy ---");
        mainBranch.getRecommendationEngine().setStrategy(new HistoryBasedRecommendationStrategy());
        recommendations = mainBranch.getRecommendationsForPatron(patron1.getPatronId());
        printRecommendations(recommendations);

        System.out.println("\n--- Changing to Popularity-based Strategy ---");
        mainBranch.getRecommendationEngine().setStrategy(new PopularityBasedRecommendationStrategy());
        recommendations = mainBranch.getRecommendationsForPatron(patron1.getPatronId());
        printRecommendations(recommendations);

        // Transfer book between branches (Multi-branch Support)
        System.out.println("\n>>> TRANSFERRING BOOKS BETWEEN BRANCHES");
        System.out.println("Transferring 'Clean Code' from Main Branch to West Branch:");
        success = mainBranch.transferBook(book4.getIsbn(), westBranch);
        System.out.println(success ? "✓ Transfer successful" : "✗ Transfer failed");

        // Display inventory summary
        System.out.println("\n>>> INVENTORY SUMMARY");
        System.out.println("\n--- Main Branch ---");
        System.out.println("Total books: " + mainBranch.getInventory().getAllBooks().size());
        System.out.println("Available books: " + mainBranch.getInventory().getAvailableBooks().size());

        System.out.println("\n--- West Branch ---");
        System.out.println("Total books: " + westBranch.getInventory().getAllBooks().size());
        System.out.println("Available books: " + westBranch.getInventory().getAvailableBooks().size());

        // Display patron statistics
        System.out.println("\n>>> PATRON STATISTICS");
        System.out.println("\nAlice's borrowing history: " + patron1.getBorrowingHistory().size() + " books");
        System.out.println("Alice's favorite genre: " + patron1.getFavoriteGenre());
        System.out.println("Bob's borrowing history: " + patron2.getBorrowingHistory().size() + " books");
        System.out.println("Bob's favorite genre: " + patron2.getFavoriteGenre());

        // Display overdue loans
        System.out.println("\n>>> OVERDUE LOANS CHECK");
        List<Loan> overdueLoans = mainBranch.getLendingService().getOverdueLoans();
        System.out.println("Overdue loans count: " + overdueLoans.size());
        if (!overdueLoans.isEmpty()) {
            overdueLoans.forEach(loan -> System.out.println("  - " + loan));
        } else {
            System.out.println("✓ No overdue loans");
        }

        // Summary
        System.out.println("\n" + "=".repeat(80));
        System.out.println("DEMONSTRATION COMPLETE");
        System.out.println("=".repeat(80));
        System.out.println("\nDesign Patterns Demonstrated:");
        System.out.println("  ✓ Singleton Pattern - LibraryManagementSystem");
        System.out.println("  ✓ Factory Pattern - StandardLibraryItemFactory");
        System.out.println("  ✓ Strategy Pattern - SearchStrategy & RecommendationStrategy");
        System.out.println("  ✓ Observer Pattern - BookReservationObserver & NotificationService");

        System.out.println("\nSOLID Principles Applied:");
        System.out.println("  ✓ Single Responsibility - Each class has one clear purpose");
        System.out.println("  ✓ Open/Closed - Extensible via strategies and observers");
        System.out.println("  ✓ Liskov Substitution - Strategy implementations are interchangeable");
        System.out.println("  ✓ Interface Segregation - Focused interfaces");
        System.out.println("  ✓ Dependency Inversion - Depend on abstractions, not concretions");

        System.out.println("\nCore Features Implemented:");
        System.out.println("  ✓ Book Management (add, remove, update, search)");
        System.out.println("  ✓ Patron Management (register, update, track history)");
        System.out.println("  ✓ Lending Process (checkout, return)");
        System.out.println("  ✓ Inventory Management (available & borrowed tracking)");

        System.out.println("\nAdvanced Features Implemented:");
        System.out.println("  ✓ Multi-branch Support with book transfers");
        System.out.println("  ✓ Reservation System with notifications");
        System.out.println("  ✓ Recommendation System with multiple strategies");

        System.out.println("\n" + "=".repeat(80));
    }

    /**
     * Configure logging for the application
     */
    private static void configureLogging() {
        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.INFO);

        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.INFO);
        handler.setFormatter(new SimpleFormatter());

        // Remove default handlers and add our custom one
        for (var h : rootLogger.getHandlers()) {
            rootLogger.removeHandler(h);
        }
        rootLogger.addHandler(handler);
    }

    /**
     * Print search results
     */
    private static void printSearchResults(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            System.out.println("Found " + books.size() + " book(s):");
            books.forEach(book -> System.out.println("  - " + book));
        }
    }

    /**
     * Print book recommendations
     */
    private static void printRecommendations(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("No recommendations available.");
        } else {
            System.out.println("Recommended " + books.size() + " book(s):");
            books.forEach(book -> System.out.println("  - " + book.getTitle() + " by " + book.getAuthor()));
        }
    }
}