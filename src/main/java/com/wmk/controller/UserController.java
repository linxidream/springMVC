package com.wmk.controller;

import com.wmk.common.exception.CustomException;
import com.wmk.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @RequestMapping(value = "/login.wmk")
    public String login(HttpServletRequest request) throws Exception {
        //如果登录失败从request中获取认证异常信息,shiroLoginFailure就是shiro异常类的全限定名
        String exceptionClassName= (String) request.getAttribute("shiroLoginFailure");
        if(exceptionClassName!=null){
            if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
                //最终会抛给异常处理器
                throw new CustomException("账号不存在");
            } else if (IncorrectCredentialsException.class.getName().equals(
                    exceptionClassName)) {
                throw new CustomException("用户名/密码错误");
            } else if("randomCodeError".equals(exceptionClassName)){
                throw new CustomException("验证码错误");
            } else{
                throw new Exception();//最终在异常处理器生成未知错误
            }
        }
//        model.addAttribute("user",request.getRemoteUser());
//        model.addAttribute("password",request.getRemoteUser());

        //此方法不处理登录成功，shiro认证成功会自动跳转到上一个路径
        //登录失败返回到login页面
        return "/index";
    }

    //系统首页
    @RequestMapping("/first.wmk")
    public String first(Model model)throws Exception{

        //从shiro的session中取出activeUser
        Subject subject= SecurityUtils.getSubject();
        //取出身份信息
        User activeUser= (User) subject.getPrincipal();
        //通过model传到页面
        model.addAttribute("activeUser",activeUser);

        return "/success";
    }

    //登陆失败页面
    @RequestMapping("/refuse.wmk")
    public String refuse(Model model)throws Exception{

        return "/refuse";
    }

    //欢迎页面
    @RequestMapping("/welcome.wmk")
    public String welcome(Model model)throws Exception{

        return "/index";

    }
}
