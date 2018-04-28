package com.wmk.controller;

import com.wmk.common.shiro.CustomRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/index")
public class indexController {
    @Autowired
    private CustomRealm customRealm;
    //欢迎页面
    @RequestMapping("/welcome.wmk")
    public String welcome(Model model)throws Exception{

        return "/welcome";

    }

    @RequestMapping("/clearShiroCache")
    public String clearShiroCache()
    {
        //清除缓存,将来开发要在service调用
        customRealm.clearCached();
        return "/welcome";
    }
}
