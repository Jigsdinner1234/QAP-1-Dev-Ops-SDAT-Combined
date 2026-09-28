# Library Management System

## Project Overview
This project is a simple object-oriented Java application that models a library. It allows staff and users to add books, register library members, borrow books, return books, and search the catalog by title, author, or genre.

The project was built to satisfy the SDAT and DevOps QAP requirement for a basic Java application with unit tests, Maven configuration, clean code practices, and GitHub Actions automation.

## Features
- Add and manage books in the library catalog
- Register library members with borrowing limits
- Issue and return books
- Search the catalog by title, author, and genre
- Restrict borrowing to valid conditions, including limits and duplicate borrow checks

## Clean Code Examples
The code follows simple, readable OOP patterns and clear method responsibilities.

### 1. Validation inside the Book model
The `Book` class keeps its own state and validates illegal actions before changing availability.

```java
public void issue() {
    if (!available) {
        throw new IllegalStateException("Book is already issued");
    }
    available = false;
}
```
This keeps business rules close to the object that owns the data.

### 2. Borrow policy inside the user class
Borrowing logic is kept inside `LibraryUser`, so the business rule is not spread across unrelated classes.

```java
public boolean borrowBook(Book book) {
    if (!canBorrowMore()) {
        return false;
    }
    if (borrowedBookIds.contains(book.getBookId())) {
        return false;
    }
    if (!book.isAvailable()) {
        return false;
    }

    book.issue();
    borrowedBookIds.add(book.getBookId());
    return true;
}
```
This keeps each class focused and easier to maintain.

### 3. Search behavior in one catalog class
The catalog centralizes search logic instead of duplicating code across the project.

```java
public List<Book> searchByAuthor(String author) {
    if (author == null || author.isBlank()) {
        return Collections.emptyList();
    }

    List<Book> matches = new ArrayList<>();
    for (Book book : books.values()) {
        if (book.getAuthor().equalsIgnoreCase(author)) {
            matches.add(book);
        }
    }
    return matches;
}
```
This makes the logic easy to test and easy to understand.

## Project Structure
- `src/main/java/com/library/Book.java` – book entity
- `src/main/java/com/library/LibraryUser.java` – user data and borrowing rules
- `src/main/java/com/library/LibraryCatalog.java` – catalog and search operations
- `src/main/java/com/library/Main.java` – sample console output
- `src/test/java/com/library/LibraryCatalogTest.java` – unit tests

## Unit Testing
This project includes 12 JUnit 5 tests that cover both positive and negative scenarios, including:
- adding books to the catalog
- registering users
- successful and failed borrowing
- return logic
- title, author, and genre search
- borrow limit enforcement
- duplicate borrow prevention
- blank search handling
- invalid state handling for loan transitions

A good mix of assertions is used, including `assertEquals`, `assertTrue`, `assertFalse`, `assertThrows`, `assertDoesNotThrow`, and `assertAll`.

## Dependencies
The project uses Maven and the following dependency:
- JUnit Jupiter 5.10.2 – downloaded from Maven Central

The build is configured in `pom.xml`.

## GitHub Actions
The CI workflow is located in `.github/workflows/java-tests.yml`.
It runs automatically on:
- pushes to `main` and `dev`
- pull requests to `main` and `dev`

The workflow installs Java 17 and runs:

```bash
mvn test
```

## Branching and Workflow Evidence
The repository follows a simple trunk-based workflow:
- `main` is the trunk
- `dev` is the integration branch
- feature work is created from `dev` and merged back

An example local workflow would be:

```bash
git checkout dev
git checkout -b feature/library-management-system
git add .
git commit -m "Implement library system"
git checkout dev
git merge --no-ff feature/library-management-system
```

## Problems Encountered
The main issue during setup was that Maven was not installed on the local machine, and Java 8 was the default runtime. The project required Java 17, so Maven and JDK 17 were installed locally before running the project and verifying the test suite.

## Passion Project Planner
A future side project idea could be a personal productivity planner or fitness habit tracker, but this QAP focuses on a simple library system to satisfy the assignment requirements while keeping the design clear and testable.

## How to Run
```bash
mvn test
```

If you want to run the sample program:

```bash
mvn exec:java -Dexec.mainClass=com.library.Main
```
