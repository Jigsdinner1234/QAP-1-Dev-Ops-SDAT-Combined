package com.library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LibraryCatalog {
    private final Map<String, Book> books = new LinkedHashMap<>();
    private final Map<String, LibraryUser> users = new LinkedHashMap<>();

    public void addBook(Book book) {
        Objects.requireNonNull(book, "Book cannot be null");
        books.put(book.getBookId(), book);
    }

    public LibraryUser registerUser(String userId, String name, int maxBooksAllowed) {
        LibraryUser user = new LibraryUser(userId, name, maxBooksAllowed);
        users.put(user.getUserId(), user);
        return user;
    }

    public Book getBook(String bookId) {
        return books.get(bookId);
    }

    public LibraryUser getUser(String userId) {
        return users.get(userId);
    }

    public boolean issueBook(String userId, String bookId) {
        LibraryUser user = users.get(userId);
        Book book = books.get(bookId);

        if (user == null || book == null) {
            return false;
        }

        return user.borrowBook(book);
    }

    public boolean returnBook(String userId, String bookId) {
        LibraryUser user = users.get(userId);
        Book book = books.get(bookId);

        if (user == null || book == null) {
            return false;
        }

        return user.returnBook(book);
    }

    public List<Book> searchByTitle(String title) {
        if (title == null || title.isBlank()) {
            return Collections.emptyList();
        }

        List<Book> matches = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                matches.add(book);
            }
        }
        return matches;
    }

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

    public List<Book> searchByGenre(String genre) {
        if (genre == null || genre.isBlank()) {
            return Collections.emptyList();
        }

        List<Book> matches = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getGenre().equalsIgnoreCase(genre)) {
                matches.add(book);
            }
        }
        return matches;
    }

    public List<Book> getAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.isAvailable()) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }
}
