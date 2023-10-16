package org.example.service;

import org.example.database.Data;
import org.example.entity.Book;
import org.example.entity.User;
import org.example.utility.InputReader;

import java.sql.SQLException;

public class BookServiceImpl implements BookService{
    @Override
    public void addBook(Book book) {
        String newBookId = "Book_"+System.nanoTime();
        book.setBookId(newBookId);
        if(Boolean.TRUE.equals(Data.searchBook(book.getBookName()))){
            Data.addBook(book);
        }else{
            System.out.println("!!!!!! Cannot add Book !!!!!!!\n");
        }
    }

    @Override
    public void printBooks() {
        Data.printAllBooks();
    }
    @Override
    public void searchBookInRange(){
        System.out.println("-----------------------------------------------------\n");
        System.out.print("Enter Book Name or Book ID : ");
        Data.searchBook(InputReader.getString());
    }
    @Override
    public void addMultipleBooks() {

        BookServiceImpl bookService = new BookServiceImpl();
        String bookName,authorName;
        double price;


        System.out.println("Enter Book Name : ");
        bookName = InputReader.getString();
        // System.out.println(bookName);

        System.out.println("Enter Author Name : ");
        authorName = InputReader.getString();
        //  System.out.println(authorName);

        System.out.println("Enter Price of Book : ");

        price = InputReader.getDoubleNumbers();

        bookService.addBook(new Book(bookName,price,authorName));
    }

    @Override
    public void addMyltipleBooks() {

    }
    @Override
    public void searchBookById(){

        System.out.print("Enter Book Name : \n");
        String bookName = InputReader.getString();

    }
    @Override
    public void issueBook() throws SQLException {
        LibrarianService librarianService = new LibrarianServiceImpl();
        User user = null;
        Book book = new Book();

        System.out.println("---------------------------------------------------------------------");
        System.out.print("Enter Book Id or Book Name: ");
        String bookName = InputReader.getString();
        Data.getBookDetails(bookName);
        System.out.println("Enter Book ID from above to Issue : ");
        bookName = InputReader.getString();
        book = Data.getBookDetails(bookName);

        System.out.print("Enter User Id of User Name : ");
        String userName = InputReader.getString();
        Data.getUserDetails(userName);
        System.out.println("Enter User ID from above to Issue : ");
        userName = InputReader.getString();
        user = Data.getUserDetails(userName);

        System.out.print("\nIs the given info of book and user correct ??(y/n)\n");
        String c = InputReader.getString();

        if (c.charAt(0)=='y' || c.charAt(0)=='Y'){
            Data.issueBook(user.getUserId(),book.getBookId());
        }
        else if(c.charAt(0)=='n' || c.charAt(0)=='N') {
            System.out.println("Do you Wish to Continue issuing Book ?? (y/n)");
            String choice = InputReader.getString();
            if (choice.charAt(0)=='y' || choice.charAt(0)=='Y'){
                issueBook();
            } else if (choice.charAt(0)=='n' || choice.charAt(0)=='N') {
                librarianService.menu();
            }
        }
    }
    @Override
    public void issuedBooks(){
        Data.printIssuedBooks();
    }
    @Override
    public void showIssuedBooks(){
        Data.printIssuedBooks();
    }
    @Override
    public void deleteUser(){
        System.out.println("Enter User Id : ");
        String userId = InputReader.getString();
        Data.removeUser(userId);
    }
    @Override
    public void issuedByStudent(User user){

        Data.issuedByMe(user);
    }

}
