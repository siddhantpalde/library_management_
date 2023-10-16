package org.example.service;

import org.example.entity.Book;
import org.example.entity.User;

import java.sql.SQLException;

public interface BookService {
    void addBook(Book book);

    void printBooks();

    void addMultipleBooks();

    void addMyltipleBooks();
    void searchBookInRange();

    void searchBookById();

    void issueBook() throws SQLException;

    void issuedBooks();

    void showIssuedBooks();

    void deleteUser();

    void issuedByStudent(User user);
}
