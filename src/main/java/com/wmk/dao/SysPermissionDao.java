package com.wmk.dao;

import com.wmk.entity.SysPermission;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysPermissionDao {

    //根据用户id查询菜单
    public List<SysPermission> findMenuListByUserId(String userid);

    public List<SysPermission> findPermissionListByUserId(String user_id);

}
