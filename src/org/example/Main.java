// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
package org.example;
import java.sql.SQLException;
import java.util.*;

import org.example.database.Data;
import org.example.entity.Book;
import org.example.entity.User;
import org.example.entity.UserType;
import org.example.service.*;
import org.example.utility.InputReader;

public class Main {
public static void main(String[] args) {
//        addInitialData();
        Data.initiateDb();
//        addAllUsers();
        mainLogin();
        LibrarianService.myMethod();
//TODO:
/*
* student menu
* Teacher Menu
* ADMIN : add analysis of books
* ADMIN : add add no of users by type of user
* ADMIN : add no of issued books
* ADMIN : add user should be only for user
* LIBRARIAN : add delete books
*
 * */
    }

    private static void adminMenu() throws Exception {
        AdminService adminService = new AdminServiceImpl();
        adminService.menu();
    }

    private static void librarianMenu() throws SQLException {
        LibrarianService librarianService = new LibrarianServiceImpl();
        librarianService.menu();
    }
    private static void studentMenu(User user){
        try{

            StudentService studentService = new StudentServiceImpl();
            studentService.studentMenu(user);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private static User loginUser() {
        System.out.println("\n!!!! USER LOGIN !!!!\n");
        System.out.print("Enter username:");
        String username = InputReader.getString();

        System.out.print("Enter password:");
        String password = InputReader.getString();

//        System.out.println("_"+username+"_"+password+"_");
        LoginService loginService = new LoginServiceImpl();
        return loginService.login(username,password);
    }

//    private static void addAllUsers() {
//        UserService userService = new UserServiceImpl();
//        userService.addUser(new User("popatpalde","popatpalde", UserType.LIBRARIAN,
//                "popatpalde@gmail.com"));
//        userService.addUser(new User("pramilapalde","pramilapalde", UserType.TEACHER,
//                "pramilapalde@gmail.com"));
//        userService.addUser(new User("chetanpalde","chetanpalde", UserType.ADMIN,
//                "chetanpalde@gmail.com"));
//        userService.addUser(new User("siddhantpalde","siddhantpalde", UserType.STUDENT,
//                "siddhantpalde@gmail.com"));
//        userService.addUser(new User("mayuripalde","mayuripalde", UserType.STUDENT,
//                "mayuripalde@gmail.com"));
//    }
    private static void  addInitialData() {
        BookService bookService = new BookServiceImpl();
        bookService.addBook(new Book("PopatBook",450,"PopatPalde"));
        bookService.addBook(new Book("PramilaBook",320,"PramilaPalde"));
        bookService.addBook(new Book("ChetanBook",600,"ChetanPalde"));
        bookService.addBook(new Book("SiddhantBook",400,"SiddhantPalde"));
        bookService.addBook(new Book("MayuriBook",350,"MayuriPalde"));
        bookService.addBook(new Book("LucyBook",250,"LucyPalde"));

    }
    private static void addBook(){
        BookServiceImpl bookService = new BookServiceImpl();
        String bookName,authorName;


    }

    public static void mainLogin(){
        User user = loginUser();
        mainLoginMenu(user);
    }
    public static void mainLoginMenu(User user){
        if(user != null) {
            try {
                switch (user.getRole()) {
                    case ADMIN:
                        adminMenu();
                        break;
                    case LIBRARIAN:
                        librarianMenu();
                        break;
                    case STUDENT:
                        studentMenu(user);
                        break;
                    case TEACHER:
                        break;
                }
            } catch (NullPointerException e) {
                System.out.println("Null pointer exception:"+e);
            } catch (Exception e) {
                System.out.println("\n!!!!!! Wrong Input !!!!!!\n----- Try Again ------");
            } finally {
                mainLoginMenu(user);
            }
        } else {
            System.out.println("\n!!!! Wrong credentials !!!!\n!!!! TRY AGAIN !!!!\n");
            mainLogin();
        }
    }
}