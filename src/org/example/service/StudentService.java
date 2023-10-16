package org.example.service;

import org.example.entity.User;

import java.sql.SQLException;

public interface StudentService {
    public void studentMenu(User user) throws SQLException;
}
