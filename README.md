# Library Management System

## Overview

A comprehensive Library Management System implemented in Java that demonstrates Object-Oriented Programming (OOP) principles, SOLID design principles, and multiple design patterns. This system helps librarians manage books, patrons, and lending processes efficiently across multiple library branches.

## Features

### Core Features
- **Book Management**: Add, remove, update, and search books by title, author, or ISBN
- **Patron Management**: Manage library members and track their borrowing history
- **Lending Process**: Complete checkout and return functionality
- **Inventory Management**: Track available and borrowed books in real-time

### Advanced Features
- **Multi-branch Support**: Manage multiple library branches with book transfer capabilities
- **Reservation System**: Reserve checked-out books with automatic notifications
- **Recommendation System**: Get personalized book recommendations based on borrowing history and preferences

## Architecture & Design

### Design Patterns Implemented

1. **Observer Pattern**: Used for the notification system when reserved books become available
2. **Factory Pattern**: Used for creating different types of library items and patrons
3. **Strategy Pattern**: Used for implementing different search and recommendation algorithms
4. **Singleton Pattern**: Used for the central library system management

### SOLID Principles

- **Single Responsibility Principle**: Each class has a single, well-defined purpose
- **Open/Closed Principle**: System is open for extension but closed for modification
- **Liskov Substitution Principle**: Derived classes can substitute base classes
- **Interface Segregation Principle**: Interfaces are client-specific rather than general-purpose
- **Dependency Inversion Principle**: High-level modules depend on abstractions, not concretions

### OOP Concepts Applied

- **Encapsulation**: Private fields with public getters/setters
- **Inheritance**: Base classes extended by specialized implementations
- **Polymorphism**: Interface implementations and method overriding
- **Abstraction**: Abstract classes and interfaces defining contracts

## Class Diagram

```
┌─────────────────────────────────────────────────────────────────────────┐
│                         LibraryManagementSystem                         │
│                              (Singleton)                                │
├─────────────────────────────────────────────────────────────────────────┤
│ - instance: LibraryManagementSystem                                     │
│ - branches: Map<String, LibraryBranch>                                  │
│ - logger: Logger                                                        │
├─────────────────────────────────────────────────────────────────────────┤
│ + getInstance(): LibraryManagementSystem                                │
│ + addBranch(branch: LibraryBranch): void                                │
│ + getBranch(branchId: String): LibraryBranch                            │
│ + getAllBranches(): List<LibraryBranch>                                 │
└─────────────────────────────────────────────────────────────────────────┘
                                    │
                                    │ manages
                                    ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                            LibraryBranch                                │
├─────────────────────────────────────────────────────────────────────────┤
│ - branchId: String                                                      │
│ - branchName: String                                                    │
│ - location: String                                                      │
│ - inventory: Inventory                                                  │
│ - patronManager: PatronManager                                          │
│ - lendingService: LendingService                                        │
│ - reservationSystem: ReservationSystem                                  │
│ - recommendationEngine: RecommendationEngine                            │
├─────────────────────────────────────────────────────────────────────────┤
│ + addBook(book: Book): void                                             │
│ + removeBook(isbn: String): boolean                                     │
│ + searchBooks(strategy: SearchStrategy): List<Book>                     │
│ + registerPatron(patron: Patron): void                                  │
│ + checkoutBook(patronId: String, isbn: String): boolean                 │
│ + returnBook(patronId: String, isbn: String): boolean                   │
│ + transferBook(isbn: String, targetBranch: LibraryBranch): boolean      │
└─────────────────────────────────────────────────────────────────────────┘
         │                    │                     │
         │ has               │ has                 │ has
         ▼                    ▼                     ▼
┌──────────────────┐  ┌──────────────────┐  ┌──────────────────┐
│    Inventory     │  │  PatronManager   │  │  LendingService  │
├──────────────────┤  ├──────────────────┤  ├──────────────────┤
│ - books: Map     │  │ - patrons: Map   │  │ - loans: List    │
│ - available: Set │  │ - logger: Logger │  │ - logger: Logger │
├──────────────────┤  ├──────────────────┤  ├──────────────────┤
│ + addBook()      │  │ + addPatron()    │  │ + checkout()     │
│ + removeBook()   │  │ + updatePatron() │  │ + returnBook()   │
│ + getBook()      │  │ + getPatron()    │  │ + getLoan()      │
│ + isAvailable()  │  │ + getAllPatrons()│  │ + getLoans()     │
└──────────────────┘  └──────────────────┘  └──────────────────┘
         │                    │                     │
         │ contains           │ contains            │ contains
         ▼                    ▼                     ▼
┌──────────────────┐  ┌──────────────────┐  ┌──────────────────┐
│      Book        │  │     Patron       │  │      Loan        │
├──────────────────┤  ├──────────────────┤  ├──────────────────┤
│ - isbn: String   │  │ - patronId: str  │  │ - loanId: String │
│ - title: String  │  │ - name: String   │  │ - book: Book     │
│ - author: String │  │ - email: String  │  │ - patron: Patron │
│ - pubYear: int   │  │ - phone: String  │  │ - checkoutDate   │
│ - genre: String  │  │ - history: List  │  │ - dueDate: Date  │
│ - copies: int    │  │ - preferences    │  │ - returnDate     │
├──────────────────┤  ├──────────────────┤  ├──────────────────┤
│ + getters()      │  │ + getters()      │  │ + isOverdue()    │
│ + setters()      │  │ + setters()      │  │ + isReturned()   │
│ + toString()     │  │ + addToHistory() │  │ + markReturned() │
└──────────────────┘  └──────────────────┘  └──────────────────┘

┌─────────────────────────────────────────────────────────────────────────┐
│                      SearchStrategy (Interface)                         │
├─────────────────────────────────────────────────────────────────────────┤
│ + search(books: Collection<Book>): List<Book>                           │
└─────────────────────────────────────────────────────────────────────────┘
                                    △
                                    │ implements
            ┌───────────────────────┼───────────────────────┐
            │                       │                       │
┌───────────────────────┐ ┌─────────────────────┐ ┌─────────────────────┐
│SearchByTitleStrategy  │ │SearchByAuthorStrategy│ │SearchByISBNStrategy │
├───────────────────────┤ ├─────────────────────┤ ├─────────────────────┤
│ - title: String       │ │ - author: String    │ │ - isbn: String      │
├───────────────────────┤ ├─────────────────────┤ ├─────────────────────┤
│ + search()            │ │ + search()          │ │ + search()          │
└───────────────────────┘ └─────────────────────┘ └─────────────────────┘

┌─────────────────────────────────────────────────────────────────────────┐
│                   BookReservationObserver (Interface)                   │
├─────────────────────────────────────────────────────────────────────────┤
│ + onBookAvailable(book: Book, patron: Patron): void                     │
└─────────────────────────────────────────────────────────────────────────┘
                                    △
                                    │ implements
                                    │
┌─────────────────────────────────────────────────────────────────────────┐
│                       NotificationService                               │
├─────────────────────────────────────────────────────────────────────────┤
│ - logger: Logger                                                        │
├─────────────────────────────────────────────────────────────────────────┤
│ + onBookAvailable(book: Book, patron: Patron): void                     │
│ + sendEmailNotification(patron: Patron, message: String): void          │
└─────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────┐
│                        ReservationSystem                                │
├─────────────────────────────────────────────────────────────────────────┤
│ - reservations: Map<String, Queue<Reservation>>                         │
│ - observers: List<BookReservationObserver>                              │
│ - logger: Logger                                                        │
├─────────────────────────────────────────────────────────────────────────┤
│ + reserveBook(patron: Patron, book: Book): boolean                      │
│ + cancelReservation(reservationId: String): boolean                     │
│ + notifyNextPatron(book: Book): void                                    │
│ + addObserver(observer: BookReservationObserver): void                  │
└─────────────────────────────────────────────────────────────────────────┘
                                    │
                                    │ contains
                                    ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                           Reservation                                   │
├─────────────────────────────────────────────────────────────────────────┤
│ - reservationId: String                                                 │
│ - patron: Patron                                                        │
│ - book: Book                                                            │
│ - reservationDate: Date                                                 │
│ - status: ReservationStatus                                             │
├─────────────────────────────────────────────────────────────────────────┤
│ + getters() / setters()                                                 │
└─────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────┐
│                      RecommendationEngine                               │
├─────────────────────────────────────────────────────────────────────────┤
│ - strategy: RecommendationStrategy                                      │
├─────────────────────────────────────────────────────────────────────────┤
│ + getRecommendations(patron: Patron, books: List<Book>): List<Book>    │
│ + setStrategy(strategy: RecommendationStrategy): void                   │
└─────────────────────────────────────────────────────────────────────────┘
                                    │
                                    │ uses
                                    ▼
┌─────────────────────────────────────────────────────────────────────────┐
│               RecommendationStrategy (Interface)                        │
├─────────────────────────────────────────────────────────────────────────┤
│ + recommend(patron: Patron, books: List<Book>): List<Book>             │
└─────────────────────────────────────────────────────────────────────────┘
                                    △
                                    │ implements
            ┌───────────────────────┼───────────────────────┐
            │                       │                       │
┌──────────────────────┐ ┌────────────────────┐ ┌──────────────────────┐
│HistoryBasedStrategy  │ │GenreBasedStrategy  │ │PopularityBased...    │
└──────────────────────┘ └────────────────────┘ └──────────────────────┘

┌─────────────────────────────────────────────────────────────────────────┐
│                     LibraryItemFactory (Abstract)                       │
├─────────────────────────────────────────────────────────────────────────┤
│ + createBook(params): Book                                              │
│ + createPatron(params): Patron                                          │
└─────────────────────────────────────────────────────────────────────────┘
                                    △
                                    │ extends
                                    │
┌─────────────────────────────────────────────────────────────────────────┐
│                   StandardLibraryItemFactory                            │
├─────────────────────────────────────────────────────────────────────────┤
│ + createBook(params): Book                                              │
│ + createPatron(params): Patron                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

## Project Structure

```
library-management-system/
├── src/
│   ├── Main.java
│   ├── models/
│   │   ├── Book.java
│   │   ├── Patron.java
│   │   ├── Loan.java
│   │   └── Reservation.java
│   ├── services/
│   │   ├── Inventory.java
│   │   ├── PatronManager.java
│   │   ├── LendingService.java
│   │   ├── ReservationSystem.java
│   │   └── RecommendationEngine.java
│   ├── strategies/
│   │   ├── SearchStrategy.java
│   │   ├── SearchByTitleStrategy.java
│   │   ├── SearchByAuthorStrategy.java
│   │   ├── SearchByISBNStrategy.java
│   │   ├── RecommendationStrategy.java
│   │   ├── HistoryBasedRecommendationStrategy.java
│   │   ├── GenreBasedRecommendationStrategy.java
│   │   └── PopularityBasedRecommendationStrategy.java
│   ├── observers/
│   │   ├── BookReservationObserver.java
│   │   └── NotificationService.java
│   ├── factory/
│   │   ├── LibraryItemFactory.java
│   │   └── StandardLibraryItemFactory.java
│   ├── core/
│   │   ├── LibraryBranch.java
│   │   └── LibraryManagementSystem.java
│   └── enums/
│       ├── ReservationStatus.java
│       └── BookGenre.java
├── README.md
└── PROJECT_BRIEF.md
```

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Java IDE (IntelliJ IDEA, Eclipse, or VS Code)

### Running the Application

1. Clone the repository:
```bash
git clone https://github.com/yourusername/library-management-system.git
cd library-management-system
```

2. Compile the Java files:
```bash
javac -d out src/**/*.java
```

3. Run the application:
```bash
java -cp out Main
```

## Usage Examples

### Basic Operations

```java
// Initialize the library system
LibraryManagementSystem lms = LibraryManagementSystem.getInstance();

// Create a library branch
LibraryBranch mainBranch = new LibraryBranch("BR001", "Main Branch", "123 Main St");
lms.addBranch(mainBranch);

// Add a book using factory
LibraryItemFactory factory = new StandardLibraryItemFactory();
Book book = factory.createBook("1984", "George Orwell", "978-0451524935", 1949, "Fiction", 5);
mainBranch.addBook(book);

// Register a patron
Patron patron = factory.createPatron("John Doe", "john@example.com", "555-1234");
mainBranch.registerPatron(patron);

// Checkout a book
mainBranch.checkoutBook(patron.getPatronId(), book.getIsbn());

// Search for books
SearchStrategy searchStrategy = new SearchByAuthorStrategy("George Orwell");
List<Book> results = mainBranch.searchBooks(searchStrategy);

// Return a book
mainBranch.returnBook(patron.getPatronId(), book.getIsbn());
```

### Advanced Features

```java
// Reserve a book
mainBranch.getReservationSystem().reserveBook(patron, book);

// Get recommendations
List<Book> recommendations = mainBranch.getRecommendationEngine()
    .getRecommendations(patron, mainBranch.getInventory().getAllBooks());

// Transfer book between branches
LibraryBranch westBranch = new LibraryBranch("BR002", "West Branch", "456 West Ave");
lms.addBranch(westBranch);
mainBranch.transferBook(book.getIsbn(), westBranch);
```

## Design Decisions

### 1. Singleton Pattern for LibraryManagementSystem
The central management system uses Singleton to ensure a single point of control for all library branches.

### 2. Strategy Pattern for Search and Recommendations
Different search and recommendation algorithms can be easily swapped without modifying client code.

### 3. Observer Pattern for Notifications
Decouples the reservation system from notification mechanisms, allowing multiple observers to react to book availability.

### 4. Factory Pattern for Object Creation
Centralizes object creation logic and allows for easy extension with different types of library items.

### 5. Separation of Concerns
Each component has a specific responsibility:
- **Inventory**: Book storage and availability tracking
- **PatronManager**: Patron data management
- **LendingService**: Loan operations and tracking
- **ReservationSystem**: Reservation queue management
- **RecommendationEngine**: Personalized recommendations

## Logging

The system uses Java's built-in `java.util.logging` framework to log important events:
- Book additions/removals
- Patron registrations
- Checkout/return operations
- Reservation notifications
- Book transfers between branches
- Errors and exceptions

## Future Enhancements

- Add support for different types of library items (DVDs, magazines, etc.)
- Implement fine calculation for overdue books
- Add user authentication and authorization
- Create a web-based UI using Spring Boot
- Integrate with a database for persistence
- Add support for e-books and digital lending
- Implement analytics and reporting features

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is created as an educational assignment to demonstrate Java and OOP concepts.

## Author

Kaustubh Warke

## Acknowledgments

- Project requirements based on Library Management System assignment brief
- Design patterns inspired by "Design Patterns: Elements of Reusable Object-Oriented Software" by Gang of Four
- SOLID principles as defined by Robert C. Martin

