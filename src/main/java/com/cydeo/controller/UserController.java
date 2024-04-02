package com.cydeo.controller;

import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {


    @GetMapping("/create")
    public String createUser(Model model){


        model.addAttribute("users",  new UserDTO());
       // model.addAttribute("roles", ); // bring me all roles from DB

        return "user/create";
    }


}
