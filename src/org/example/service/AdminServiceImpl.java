package org.example.service;

import org.example.Main;
import org.example.utility.InputReader;

import java.sql.SQLException;

public class AdminServiceImpl implements AdminService {
    @Override
    public void menu() throws SQLException {
        ;;;;;;;
        BookService addMultipleService = new BookServiceImpl();
        UserService userService = new UserServiceImpl();
        int choice;
        do {
            System.out.println("\n-----ADMIN MENU------\n");
            System.out.println("1.Add new User");
            System.out.println("2.Delete a User");
            System.out.println("3.Show all Users");
            System.out.println("4.Print all Issued Books");
            System.out.println("5.Logout");
            System.out.println("\n_________________________________");
            System.out.printf("\n--ENTER YOUR CHOICE : ");
            choice = InputReader.getNumbers();

            switch (choice){
                case 1:
                    userService.addUserDetails();
                    break;
                case 2 :
                    userService.deleteUser();
                    break;
                case 3:
                    userService.printUsers();
                    break;
                case 4:
                    addMultipleService.showIssuedBooks();
                    break;
                case 5 :
                    Main.mainLogin();
            }
        }while(choice < 6);
    }


}
