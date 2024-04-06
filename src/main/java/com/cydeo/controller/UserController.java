package com.cydeo.controller;

import com.cydeo.dto.UserDTO;
import com.cydeo.service.RoleService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {


    // 2 dependency injection, using interface inject for loosing couple
    RoleService roleService;
    UserService userService;

    public UserController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String createUser(Model model){


        model.addAttribute("userDTO", new UserDTO());  // collect the data from UI into new UserDTO
        model.addAttribute("roles", roleService.findAll()); // bring me all roles from DB
        model.addAttribute("users", userService.findAll());  // bring all the users from DB

        return "user/create";
    }


    @PostMapping("/create") // this mapping helps to collect UI input userDTO and post it // use same endpoint as getMapping
    public String insertUser(@ModelAttribute("userDTO") UserDTO userDTO, Model model){

        userService.save(userDTO); // save the user into DB

        return "redirect:/user/create"; // redirect to getMapping method
    }

    @GetMapping("/edit/{username}")
    public String updateUser(@PathVariable("username") String username, Model model){
        // get the username from the UI with pathVariable
        // define the attributes

        model.addAttribute("userDTO", userService.findById(username));  // collect the data from UI into new UserDTO
        model.addAttribute("roles", roleService.findAll()); // bring me all roles from DB
        model.addAttribute("users", userService.findAll());

        return "/user/update";
    }

    @PostMapping("/edit")
    public String updateUser(UserDTO user){

        userService.update(user);

        return "redirect:/user/create";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") String sameID){

        userService.deleteById(sameID);

        return "redirect:/user/create";
    }





}
