package com.wmk.common.shiro;

import com.wmk.entity.User;
import com.wmk.service.UserService;
import com.wmk.serviceImple.UserServiceImple;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    //设置realm的名称
    @Override
    public void setName(String name) {
        super.setName("customRealm");
    }

    //用于授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //token是用户输入的
        //第一步:丛token中取出身份信息
        String userCode= (String) token.getPrincipal();

        //第二步:根据用户输入的userCode丛数据库查询
        //activeUser就是用户的身份信息
        User activeUser=new User();
        activeUser.setUser_id(4);
        activeUser.setUser_name("zhangsan");
        activeUser.setUser_password("123456");
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

        ///将activeUser设置到simpleAuthenticationInfo
        SimpleAuthenticationInfo simpleAuthenticationInfo=new
                SimpleAuthenticationInfo(activeUser,password,this.getName());


        return simpleAuthenticationInfo;
    }
}
