package com.library;

import java.util.Objects;

public class Book {
    private final String bookId;
    private final String title;
    private final String author;
    private final String genre;
    private boolean available;

    public Book(String bookId, String title, String author, String genre) {
        this.bookId = Objects.requireNonNull(bookId, "Book ID cannot be null");
        this.title = Objects.requireNonNull(title, "Title cannot be null");
        this.author = Objects.requireNonNull(author, "Author cannot be null");
        this.genre = Objects.requireNonNull(genre, "Genre cannot be null");
        this.available = true;
    }

    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public boolean isAvailable() {
        return available;
    }

    public void issue() {
        if (!available) {
            throw new IllegalStateException("Book is already issued");
        }
        available = false;
    }

    public void returnBook() {
        if (available) {
            throw new IllegalStateException("Book is already available in the library");
        }
        available = true;
    }
}
