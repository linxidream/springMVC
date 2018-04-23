package com.wmk.dao;

import com.wmk.common.shiro.pojo.SysUserExample;
import com.wmk.entity.SysUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysDao {
    List<SysUser> selectByExample(SysUserExample example);

    SysUser selectByUserName(String user_name);
}
