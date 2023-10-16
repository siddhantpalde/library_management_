package org.example.service;

import org.example.database.Data;
import org.example.entity.*;
import org.example.utility.InputReader;

import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class UserServiceImpl implements UserService{
    @Override
    public void addUserDetails(){
        User user;

        System.out.println("Enter UserName For New User : ");
        String name = InputReader.getString();
        System.out.println("Enter PassWord");
        String password = InputReader.getString();
        System.out.println("Enter Role : ");
        System.out.println("\t1.ADMIN");
        System.out.println("\t2.LIBRARIAN");
        System.out.println("\t3.STUDENT");
        System.out.println("\t4.TEACHER");
        System.out.println("Enter your choice : ");
        int role = InputReader.getNumbers();
        System.out.println("Enter Email id :");
        String email = InputReader.getString();
//        if(!Data.checkUserByEmail(email)){
        user = new User(name,password,assignUserType(role),email);
        addUser(user);
//            System.out.println("User Succesfully added as \n"
//                            +"\nUser ID : "+ user.getUserId()
//                            + "\nUser Name"+name
//                            +"\nEmail Id : "+user.getEmailId()
//                            +" \nUser Type : " +assignUserType(role)
//                    );
//        }


    }
    @Override
    public void addUser(User user) {
        String encodedPassword = Base64.getEncoder().encodeToString(user.getPassword().getBytes());
        user.setPassword(encodedPassword);
        String newUserId = "User_"+System.nanoTime();
        user.setUserId(newUserId);
        Data.insertUser(user);
    }
    @Override
    public UserType assignUserType(int role){
        User user = new User();
        UserType userRole = null;
        if (role == 1){
            userRole = UserType.ADMIN;
        } else if (role == 2) {
            userRole = UserType.LIBRARIAN;
        } else if (role == 3) {
            userRole = UserType.STUDENT;
        } else if (role == 4) {
            userRole = UserType.TEACHER;
        }else {
            System.out.println("\nWrong Choice\n");
        }
        return userRole;
    }
    @Override
    public void deleteUser() throws SQLException {
//        System.out.println("Enter User Id to Delete :");
//        String userId = InputReader.getString();
        User user = Data.delUserFromDb();
        System.out.println(user.toString());
        System.out.println("-------------------------------------------------------------------------");
    }
    @Override
    public void printAllUsers(){
        List<User> userList = Data.getUsers();
        for (User user : userList){
            System.out.println(user.toString());
        }
    }
    @Override
    public User searchUser(){
        List<User> userList = Data.getUsers();
        boolean flag = false;
        User user=new User();

        System.out.println("Enter User ID : ");
        String id = InputReader.getString();

        for (User users : userList){
            if((users.getUserId()).equals(id)){
                flag = true;
                user= users;
                return user;
            }
        }
        if(!flag){
            System.out.println("User Not Found");
            searchUser();
        }
        if ((user.getUserId()).equals(" ")){
            System.out.println("NO user Found");
        }
        return user;
    }
    @Override
    public void printUsers(){
        Data.printAllUsers();
    }
}
