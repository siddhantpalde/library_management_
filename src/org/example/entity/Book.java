package org.example.entity;

public class Book {
    private String bookId;
    private String bookName;
    private double bookPrice;
    //    private Author author;
    private String author;

    public Book() {}

    public Book(String bookName, double bookPrice, String author) {
        this.bookName = bookName;
        this.bookPrice = bookPrice;
        this.author = author;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Book{");
        sb.append("bookId='").append(bookId).append('\'');
        sb.append(", bookName='").append(bookName).append('\'');
        sb.append(", bookPrice=").append(bookPrice);
        sb.append(", author='").append(author).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
