package com.wmk.service;

import com.wmk.entity.SysPermission;
import com.wmk.entity.SysUser;

import java.util.List;

public interface SysService {
    public SysUser findSysUserByUserCode(String userCode);

    //根据用户id查询权限范围的菜单
    public List<SysPermission> findMenuListByUserId(String userId);
}
