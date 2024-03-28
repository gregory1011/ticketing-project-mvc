package com.cydeo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping(value = {"/login","/"})  /// these 2 endpoints are same page, login page
    public String login(){

        return "login";
    }

    @RequestMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

}