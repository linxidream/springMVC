package com.wmk.controller;

import com.wmk.entity.User;
import com.wmk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/test")
public class testController {

    @Autowired
    private UserService userService;

    @RequestMapping("/test1.wmk")
    public String test1(Model model){
        model.addAttribute("user","wmk");
        return "test";
    }

    @RequestMapping("/aaa.wmk")
    public String aaa(Model model){
        model.addAttribute("user","wmk");
        return "aaa";
    }

    @RequestMapping("/a.wmk")
    @ResponseBody
    public String aaa1(){
        return "aaa";
    }

    @GetMapping("/all/user.wmk")
    @ResponseBody
    public List<User> getAllUser(){
        return userService.selectAllUser();
    }
}
