# Quick Start Guide - Library Management System

## Prerequisites
- Java Development Kit (JDK) 8 or higher
- Terminal/Command Prompt access

## Quick Run (Windows PowerShell)

### One-Command Compile and Run
```powershell
# Navigate to project directory
cd C:\Actions\kaustubhwarke\library-management-system

# Compile and run in one go
if (!(Test-Path "out")) { New-Item -ItemType Directory -Path "out" | Out-Null }; Get-ChildItem -Path src -Filter *.java -Recurse | ForEach-Object { $_.FullName } | Out-File -FilePath sources.txt -Encoding ASCII; javac -d out -encoding UTF-8 "@sources.txt"; if ($?) { java -cp out Main }; Remove-Item sources.txt -ErrorAction SilentlyContinue
```

### Step-by-Step

1. **Compile:**
```powershell
# Create output directory
New-Item -ItemType Directory -Force -Path "out" | Out-Null

# Create list of all Java files
Get-ChildItem -Path src -Filter *.java -Recurse | ForEach-Object { $_.FullName } | Out-File -FilePath sources.txt -Encoding ASCII

# Compile all files
javac -d out -encoding UTF-8 "@sources.txt"

# Clean up
Remove-Item sources.txt
```

2. **Run:**
```powershell
java -cp out Main
```

## Quick Run (Linux/Mac)

### Compile and Run
```bash
# Navigate to project directory
cd /path/to/library-management-system

# Compile
mkdir -p out
find src -name "*.java" > sources.txt
javac -d out -encoding UTF-8 @sources.txt

# Run
java -cp out Main

# Clean up
rm sources.txt
```

## Expected Output

The application will demonstrate:
1. ✅ Creating 2 library branches
2. ✅ Adding 7 books to inventory
3. ✅ Registering 3 patrons
4. ✅ Searching books (by title, author, ISBN)
5. ✅ Checking out 4 books
6. ✅ Creating 2 reservations
7. ✅ Returning 2 books (with notifications)
8. ✅ Getting recommendations (3 different strategies)
9. ✅ Transferring book between branches
10. ✅ Displaying inventory summary
11. ✅ Showing patron statistics
12. ✅ Checking for overdue loans

## What You'll See

### Design Patterns in Action
- **Singleton:** Single LibraryManagementSystem instance
- **Factory:** Creating books and patrons
- **Strategy:** Different search and recommendation algorithms
- **Observer:** Notifications when reserved books become available

### SOLID Principles
- Each class has a single responsibility
- System is extensible without modification
- Strategy implementations are interchangeable
- Interfaces are focused and specific
- Dependencies on abstractions, not concretions

## Project Statistics

- **27 Java Files** across 8 packages
- **4 Design Patterns** implemented
- **5 SOLID Principles** applied
- **All Core + Optional Features** complete

## Troubleshooting

### Issue: "javac: command not found"
**Solution:** Ensure Java JDK is installed and in PATH
```powershell
# Check Java installation
java -version
javac -version
```

### Issue: Compilation errors
**Solution:** Ensure you're in the project root directory
```powershell
# Verify you're in the right directory
Get-Location
# Should show: C:\Actions\kaustubhwarke\library-management-system
```

### Issue: "Could not find or load main class Main"
**Solution:** Ensure compilation was successful and `out` directory exists
```powershell
# Check if out directory has compiled classes
Get-ChildItem -Path out -Recurse
```

## Testing Individual Features

### Test Book Search
The demo includes searches by:
- Author: "Orwell" → Finds "1984"
- Title: "Lord" → Finds "The Lord of the Rings"
- ISBN: "978-0061120084" → Finds "To Kill a Mockingbird"

### Test Lending System
- Alice checks out 2 books
- Bob checks out 1 book
- Carol checks out 1 book
- All tracked with due dates

### Test Reservation System
- Carol reserves "1984" (already borrowed)
- Bob also reserves "1984" (queued)
- When Alice returns it, Carol gets notified (Observer Pattern)

### Test Recommendations
Three strategies demonstrated:
1. **Genre-based:** Books from patron's favorite genre
2. **History-based:** Books patron hasn't read yet
3. **Popularity-based:** Most popular books

### Test Multi-branch
- "Clean Code" transferred from Main Branch to West Branch
- Inventory updated in both branches

## File Structure Reference

```
src/
├── Main.java                 # Application entry point
├── core/                     # Core system classes
│   ├── LibraryBranch.java
│   └── LibraryManagementSystem.java
├── models/                   # Entity classes
│   ├── Book.java
│   ├── Patron.java
│   ├── Loan.java
│   └── Reservation.java
├── services/                 # Business logic services
│   ├── Inventory.java
│   ├── PatronManager.java
│   ├── LendingService.java
│   ├── ReservationSystem.java
│   └── RecommendationEngine.java
├── strategies/               # Strategy pattern implementations
│   ├── SearchStrategy.java
│   ├── SearchBy*.java (3 files)
│   ├── RecommendationStrategy.java
│   └── *RecommendationStrategy.java (3 files)
├── observers/                # Observer pattern implementations
│   ├── BookReservationObserver.java
│   └── NotificationService.java
├── factory/                  # Factory pattern implementations
│   ├── LibraryItemFactory.java
│   └── StandardLibraryItemFactory.java
└── enums/                    # Enum types
    ├── BookGenre.java
    └── ReservationStatus.java
```

## Next Steps

1. ✅ Code is complete and tested
2. ✅ Documentation is comprehensive
3. ✅ Ready for GitHub submission
4. 📤 Create Pull Request with your implementation

## Support

For questions or issues:
1. Check IMPLEMENTATION_SUMMARY.md for detailed documentation
2. Review README.md for architecture and class diagram
3. Read inline JavaDoc comments in source files

---

**Status:** ✅ Ready for Submission
**Implementation:** 100% Complete
**Testing:** All features verified

