package com.library;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LibraryCatalogTest {

    private LibraryCatalog catalog;

    @BeforeEach
    void setUp() {
        catalog = new LibraryCatalog();
        catalog.addBook(new Book("B-101", "Clean Code", "Robert C. Martin", "Technology"));
        catalog.addBook(new Book("B-102", "The Pragmatic Programmer", "Andrew Hunt", "Technology"));
        catalog.addBook(new Book("B-103", "Pride and Prejudice", "Jane Austen", "Classic"));
    }

    @Test
    void itAddsBookToCatalog() {
        Book book = catalog.getBook("B-101");

        assertAll(
                () -> assertNotNull(book),
                () -> assertEquals("Clean Code", book.getTitle()),
                () -> assertTrue(book.isAvailable()));
    }

    @Test
    void itRegistersUser() {
        LibraryUser user = catalog.registerUser("U-1001", "Alice", 2);

        assertAll(
                () -> assertNotNull(user),
                () -> assertEquals("Alice", user.getName()),
                () -> assertEquals(2, user.getMaxBooksAllowed()));
    }

    @Test
    void itIssuesBookWhenAvailable() {
        LibraryUser user = catalog.registerUser("U-1002", "Bob", 2);

        boolean success = catalog.issueBook("U-1002", "B-101");

        assertAll(
                () -> assertTrue(success),
                () -> assertFalse(catalog.getBook("B-101").isAvailable()),
                () -> assertEquals(1, user.getBorrowedBookCount()));
    }

    @Test
    void itRejectsIssueForUnknownUser() {
        boolean success = catalog.issueBook("U-999", "B-101");

        assertFalse(success);
    }

    @Test
    void itRejectsIssueWhenBookIsUnavailable() {
        LibraryUser user = catalog.registerUser("U-1003", "Cara", 2);
        catalog.issueBook("U-1003", "B-101");

        boolean secondAttempt = catalog.issueBook("U-1003", "B-102");

        assertAll(
                () -> assertTrue(secondAttempt),
                () -> assertTrue(catalog.getBook("B-101").isAvailable() == false),
                () -> assertEquals(2, user.getBorrowedBookCount()));
    }

    @Test
    void itReturnsBookToCatalog() {
        LibraryUser user = catalog.registerUser("U-1004", "David", 2);
        catalog.issueBook("U-1004", "B-101");

        boolean returned = catalog.returnBook("U-1004", "B-101");

        assertAll(
                () -> assertTrue(returned),
                () -> assertTrue(catalog.getBook("B-101").isAvailable()),
                () -> assertEquals(0, user.getBorrowedBookCount()));
    }

    @Test
    void itSearchesBookByTitle() {
        List<Book> matches = catalog.searchByTitle("Clean Code");

        assertAll(
                () -> assertEquals(1, matches.size()),
                () -> assertEquals("B-101", matches.get(0).getBookId()));
    }

    @Test
    void itSearchesBookByAuthor() {
        List<Book> matches = catalog.searchByAuthor("Andrew Hunt");

        assertAll(
                () -> assertEquals(1, matches.size()),
                () -> assertEquals("B-102", matches.get(0).getBookId()));
    }

    @Test
    void itSearchesBookByGenre() {
        List<Book> matches = catalog.searchByGenre("Technology");

        assertEquals(2, matches.size());
    }

    @Test
    void itRefusesBorrowWhenUserExceedsLimit() {
        LibraryUser user = catalog.registerUser("U-1005", "Emma", 1);

        assertAll(
                () -> assertTrue(catalog.issueBook("U-1005", "B-101")),
                () -> assertFalse(catalog.issueBook("U-1005", "B-102")),
                () -> assertEquals(1, user.getBorrowedBookCount()));
    }

    @Test
    void itPreventsBorrowingTheSameBookTwice() {
        LibraryUser user = catalog.registerUser("U-1006", "Frank", 2);

        assertAll(
                () -> assertTrue(catalog.issueBook("U-1006", "B-101")),
                () -> assertFalse(catalog.issueBook("U-1006", "B-101")));
    }

    @Test
    void itReturnsEmptyListForBlankSearch() {
        assertAll(
                () -> assertTrue(catalog.searchByTitle("   ").isEmpty()),
                () -> assertTrue(catalog.searchByAuthor("\n").isEmpty()),
                () -> assertTrue(catalog.searchByGenre("").isEmpty()));
    }

    @Test
    void itThrowsWhenBookIsAlreadyAvailableOrIssued() {
        Book book = new Book("B-104", "Refactoring", "Martin Fowler", "Technology");
        book.issue();

        assertThrows(IllegalStateException.class, book::issue);
        assertDoesNotThrow(() -> {
            book.returnBook();
            book.issue();
        });
    }
}
