package com.wmk.service;

import com.wmk.entity.User;

import java.util.List;

public interface UserService {
    public List<User> selectAllUser();

    public User findUserByName(String user_name);
}
