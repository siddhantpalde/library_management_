package org.example.service;

import org.example.database.Data;
import org.example.entity.User;

public class LoginServiceImpl implements LoginService {
    @Override
    public User login(String username, String password) {
        User user = null;
        user = Data.checkLogin(username,password);

        if( user != null ){
            System.out.println("Login successful");
            return user;
        }
        return null;
    }
}
