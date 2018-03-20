package com.wmk.dao;

import com.wmk.entity.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Repository
public interface UserDao {

    List<User> selectAllUser();

}
