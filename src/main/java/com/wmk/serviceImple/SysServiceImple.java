package com.wmk.serviceImple;

import com.wmk.common.shiro.pojo.SysUserExample;
import com.wmk.dao.SysDao;
import com.wmk.dao.SysPermissionDao;
import com.wmk.entity.SysPermission;
import com.wmk.entity.SysUser;
import com.wmk.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SysServiceImple implements SysService{

    @Autowired
    private SysDao sysDao;
    @Autowired
    private SysPermissionDao sysPermissionDao;

    /**
     * 根据账号查询用户信息
     * @param userCode
     * @return
     */
    @Override
    public SysUser findSysUserByUserCode(String userCode) {
        SysUserExample sysUserExample=new SysUserExample();
        SysUserExample.Criteria criteria=sysUserExample.createCriteria();
        criteria.andUsercodeEqualTo(userCode);

        List<SysUser> list=sysDao.selectByExample(sysUserExample);

        if (list!=null&&list.size()==1)
        {
            return list.get(0);
        }

        return null;
    }

    @Override
    public List<SysPermission> findMenuListByUserId(String userId) {
        return sysPermissionDao.findMenuListByUserId(userId);
    }
}
