package com.wmk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class testController {
    @RequestMapping("/test1.wmk")
    public String test1(Model model){
        model.addAttribute("user","wmk");
        return "test";
    }

    @RequestMapping("/aaa.wmk")
    public String aaa(){
        return "aaa";
    }

    @RequestMapping("/a.wmk")
    @ResponseBody
    public String aaa1(){
        return "aaa";
    }
}
