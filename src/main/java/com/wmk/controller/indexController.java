package com.wmk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/index")
public class indexController {
    //欢迎页面
    @RequestMapping("/welcome.wmk")
    public String welcome(Model model)throws Exception{

        return "/welcome";

    }
}
