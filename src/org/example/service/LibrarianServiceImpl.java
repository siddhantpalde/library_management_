package org.example.service;

import org.example.Main;
import org.example.utility.InputReader;

import java.sql.SQLException;

public class LibrarianServiceImpl  implements LibrarianService {
    @Override
    public void defaultMethod() {

    }

    @Override
    public void menu() throws SQLException {
        BookService addMultipleService = new BookServiceImpl();
        int choice;
        do {
            System.out.println("----------- LIBRARIAN MENU ----------------");
            System.out.println("1.Search Book");//
            System.out.println("2.Add Book");//
            System.out.println("3.Print all Books");//
            System.out.println("4.Print all issued Books");//
            System.out.println("5:Issue Book");
            System.out.println("6.Logout");
            System.out.println("-----------------------------------");
            System.out.print("Enter Your Choice : ");

            choice = InputReader.getNumbers();

            switch (choice){//return book remaining
                case 1:
                    addMultipleService.searchBookInRange();
                    break;
                case 2 :
                    addMultipleService.addMultipleBooks();
                    break;
                case 3:
                    addMultipleService.printBooks();
                    break;
                case 4:addMultipleService.issuedBooks();
                    break;
                case 5:addMultipleService.issueBook();
                    break;
                case 6:
                    System.out.println("!!!!!!!! Logout Succesfull !!!!!!!!");
                    Main.mainLogin();
//                default:
//                    System.out.println("Wrong Choice!!!!!!!");
//                    break;
            }
        }while(choice < 7);
    }

}
