package com.cydeo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    //localhost:8080/ or localhost:8080/login
    @RequestMapping(value = {"/login","/"})  /// these 2 endpoints show same page, login page
    public String login(){
        return "login";
    }

    @RequestMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

}