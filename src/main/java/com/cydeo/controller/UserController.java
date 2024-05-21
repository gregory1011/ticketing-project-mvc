package com.cydeo.controller;

import com.cydeo.dto.UserDTO;
import com.cydeo.service.RoleService;
import com.cydeo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {


    // 2 dependency injection, using interface inject for loosing couple
    private final RoleService roleService;
    private final UserService userService;


    @GetMapping("/create")
    public String createUser(Model model){


        model.addAttribute("userDTO", new UserDTO());  // collect the data from UI into new UserDTO
        model.addAttribute("roles", roleService.listAllRoles()); // bring me all roles from DB
        model.addAttribute("users", userService.listAllUsers());  // bring all the users from DB

        return "user/create";
    }


    @PostMapping("/create") // this mapping helps to collect UI input userDTO and post it // use same endpoint as getMapping
    public String insertUser(@Valid @ModelAttribute("userDTO") UserDTO userDTO, BindingResult bindingResult, Model model){

//         validation
//        if (bindingResult.hasErrors()){
//
//            model.addAttribute("roles", roleService.listAllRoles());
//            model.addAttribute("users", userService.listAllUsers());
//            return "user/create";
//        }

        userService.save(userDTO); // save the user into DB

        return "redirect:/user/create"; // redirect to getMapping method
    }

    @GetMapping("/edit/{username}")
    public String updateUser(@PathVariable("username") String username, Model model){
        // get the username from the UI with pathVariable
        // define the attributes

        model.addAttribute("userDTO", userService.findUserByUserName(username));  // collect the data from UI into new UserDTO
        model.addAttribute("roles", roleService.listAllRoles()); // bring me all roles from DB
        model.addAttribute("users", userService.listAllUsers());

        return "/user/update";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") UserDTO user){

        userService.update(user);

        return "redirect:/user/create";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") String username){

        userService.delete(username);

        return "redirect:/user/create";
    }


}
