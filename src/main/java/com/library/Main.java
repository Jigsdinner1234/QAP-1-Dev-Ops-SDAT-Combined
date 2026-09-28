package com.library;

public class Main {
    public static void main(String[] args) {
        LibraryCatalog catalog = new LibraryCatalog();

        catalog.addBook(new Book("B-1001", "Clean Code", "Robert C. Martin", "Technology"));
        catalog.addBook(new Book("B-1002", "The Hobbit", "J.R.R. Tolkien", "Fantasy"));
        catalog.addBook(new Book("B-1003", "Atomic Habits", "James Clear", "Self-Development"));

        LibraryUser user = catalog.registerUser("U-101", "Alice", 2);

        System.out.println("Available books: " + catalog.getAvailableBooks().size());
        System.out.println("Borrow successful: " + catalog.issueBook(user.getUserId(), "B-1001"));
        System.out.println("User borrowed count: " + user.getBorrowedBookCount());
    }
}
