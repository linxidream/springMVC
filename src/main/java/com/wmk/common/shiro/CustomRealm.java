package com.wmk.common.shiro;

import com.wmk.common.shiro.pojo.ActiveUser;
import com.wmk.dao.SysPermissionDao;
import com.wmk.entity.SysPermission;
import com.wmk.entity.SysUser;
import com.wmk.entity.User;
import com.wmk.service.SysService;
import com.wmk.service.UserService;
import com.wmk.serviceImple.UserServiceImple;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private SysService sysService;

    @Autowired
    private SysPermissionDao sysPermissionDao;

    //设置realm的名称
    @Override
    public void setName(String name) {
        super.setName("customRealm");
    }

    //认证回调函数，登录使用
/*
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //token是用户输入的
        //第一步:丛token中取出身份信息
        String userCode= (String) token.getPrincipal();
        //第二步:根据用户输入的userCode丛数据库查询
        //activeUser就是用户的身份信息
        User activeUser=new User();
        activeUser.setUser_name("444");
        //根据用户id取出菜单
        //通过service取出菜单
        User user = new User();
        try {
            user=userService.findUserByName(activeUser.getUser_name());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //判断是否从数据库中查询到用户信息
        if(userService == null){
            return null;
        }
        //将用户菜单设置到activeUser
        activeUser.setUser_role(user.getUser_role());
        //模拟丛数据库查询到的密码
        String password=user.getUser_password();
        //如果查不到返回null，
        //如果查询到，返回认证信息AuthenticationInfo
        activeUser.setUser_password(password);
        ///将activeUser设置到simpleAuthenticationInfo
        SimpleAuthenticationInfo simpleAuthenticationInfo=new
                SimpleAuthenticationInfo(activeUser,password,this.getName());
        return simpleAuthenticationInfo;
    }
*/
/*    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

        //验证用户名密码
        User user = userService.findUserByName(token.getUsername());
        if(user != null){
            return new SimpleAuthenticationInfo(user,user.getUser_password(),this.getName());
        }else{
            return null;
        }
    }*/

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
        //token是用户输入的
        //第一步:丛token中取出身份信息
        String userCode= (String) authcToken.getPrincipal();

        //第二步:根据用户输入的userCode丛数据库查询
        SysUser sysUser =null;
        try {
            sysUser=sysService.findSysUserByUserCode(userCode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //判断是否从数据库中查询到用户信息
        if (sysService==null)
        {
            return null;
        }

        ActiveUser activeUser=new ActiveUser();
        activeUser.setUserid(sysUser.getId());
        activeUser.setUsercode(sysUser.getUsercode());
        activeUser.setUsername(sysUser.getUsername());

        List<SysPermission> menus=null;

        List<SysPermission> permissions = null;
        try {
            menus=sysService.findMenuListByUserId(sysUser.getId());
            permissions = sysPermissionDao.findPermissionListByUserId(sysUser.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //将用户菜单设置到activeUser
        activeUser.setMenus(menus);
        activeUser.setPermissions(permissions);
        ///将activeUser设置到simpleAuthenticationInfo
        SimpleAuthenticationInfo simpleAuthenticationInfo=new
                SimpleAuthenticationInfo(activeUser,sysUser.getPassword(),this.getName());


        return simpleAuthenticationInfo;

    }

    /**
     * 获取权限授权信息，如果缓存中存在，则直接从缓存中获取，否则就重新获取， 登录成功后调用
     */
/*
    protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            return null;
        }
        AuthorizationInfo info = null;
        if (info == null) {
            info = doGetAuthorizationInfo(principals);
        }

        return info;
    }
*/



    //用于授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        ActiveUser activeUser= (ActiveUser) principalCollection.getPrimaryPrincipal();

        List<SysPermission> permissionList=null;
        try {
            permissionList=sysPermissionDao.findPermissionListByUserId(activeUser.getUserid());
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> permissions=new ArrayList<String>();

        if (permissionList!=null)
        {
            for (SysPermission sysPermission:permissionList)
            {
                //将数据库中的权限标签符放入集合
                permissions.add(sysPermission.getPercode());
            }
        }

        //查到权限数据，返回授权信息(包括上边的permissions)
        SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();

        //将上边查询到授权信息填充到simpleAuthorizationInfo对象中
        simpleAuthorizationInfo.addStringPermissions(permissions);

        return simpleAuthorizationInfo;
      /*  Principal principal = (Principal) getAvailablePrincipal(principalCollection);
        Subject subject = SecurityUtils.getSubject();
        Collection<Session> sessions = sessionDAO.getActiveSessions();

        if(sessions.size() > 0){
            if(subject.isAuthenticated()){
                for(Session session : sessions){
                    sessionDAO.delete(session);
                }
            }else{
                subject.logout();
                throw new AuthenticationException("账号已在其他地方登录，请重新登陆");
            }
        }

        User user = userService.findUserByName(principal.getName());

        if(user != null) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            List<Menu> list = UserUtils.getMenuList();
            for (Menu menu : list) {
                if (StringUtils.isNotBlank(menu.getPermission())) {
                    // 添加基于Permission的权限信息
                    for (String permission : StringUtils.split(menu.getPermission(), ",")) {
                        info.addStringPermission(permission);
                    }
                }
            }
            info.addStringPermission("user");
            for(Role role : user.getRoleList){
                info.addRole(role.getEnname());
            }
            return info;
        }
        return null;*/
    }
}
