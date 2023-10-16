package org.example.service;

import org.example.entity.User;

public interface LoginService {
    User login(String username, String password);

}
