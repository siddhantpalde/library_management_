package org.example.service;

import org.example.entity.User;
import org.example.entity.UserType;

import java.sql.SQLException;

public interface UserService {
    void addUserDetails();

    void addUser(User user);


    UserType assignUserType(int role);

    void deleteUser() throws SQLException;

    void printAllUsers();


    User searchUser();

    void printUsers();
}
