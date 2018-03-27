package com.wmk.dao;

import com.wmk.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    List<User> selectAllUser();

    User findUserByName(String user_name);
}
