package com.library;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class LibraryUser {
    private final String userId;
    private final String name;
    private final int maxBooksAllowed;
    private final Set<String> borrowedBookIds;

    public LibraryUser(String userId, String name, int maxBooksAllowed) {
        this.userId = Objects.requireNonNull(userId, "User ID cannot be null");
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        if (maxBooksAllowed <= 0) {
            throw new IllegalArgumentException("Borrow limit must be greater than zero");
        }
        this.maxBooksAllowed = maxBooksAllowed;
        this.borrowedBookIds = new HashSet<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public int getMaxBooksAllowed() {
        return maxBooksAllowed;
    }

    public boolean canBorrowMore() {
        return borrowedBookIds.size() < maxBooksAllowed;
    }

    public boolean borrowBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
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

    public boolean returnBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (!borrowedBookIds.contains(book.getBookId())) {
            return false;
        }

        borrowedBookIds.remove(book.getBookId());
        book.returnBook();
        return true;
    }

    public int getBorrowedBookCount() {
        return borrowedBookIds.size();
    }
}
