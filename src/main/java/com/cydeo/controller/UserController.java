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
@RequestMapping("/user") //    /user  - endPoint
public class UserController {


    // 2 dependency injection, using @AllArgConst inject for loosing couple
    private final RoleService roleService;
    private final UserService userService;


    @GetMapping("/create") //      /user/create - endpoint
    public String createUser(Model model){


        model.addAttribute("userDTO", new UserDTO());  // collect the data from UI into new UserDTO
        model.addAttribute("roles", roleService.listAllRoles()); // bring me all roles from DB
        model.addAttribute("users", userService.listAllUsers());  // bring all the users from DB

        return "user/create";
    }


    @PostMapping("/create") // this mapping helps to collect UI input userDTO and post it // use same endpoint as getMapping
    public String insertUser(@Valid @ModelAttribute("userDTO") UserDTO userDTO, BindingResult bindingResult, Model model){

        // validation
        if (bindingResult.hasErrors()){

            model.addAttribute("roles", roleService.listAllRoles());
            model.addAttribute("users", userService.listAllUsers());
//            List<FieldError> err = bindingResult.getFieldErrors();
//
//            for (FieldError e : err){
//                System.out.println("Error on object ----->" + e.getObjectName()+ " on field ----->"+e.getField()+". Message --->"+e.getDefaultMessage());
//            }
            return "user/create";
        }

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
    public String updateUser( @ModelAttribute("userDTO") UserDTO userDTO, BindingResult bindingResult, Model model){

        // we use this if statement to capture the error with BindingResult
        if (bindingResult.hasErrors()){ // if true read body code and return /user/update

            model.addAttribute("roles", roleService.listAllRoles());
            model.addAttribute("users", userService.listAllUsers());
            return "/user/update";
        }

        userService.update(userDTO);

        return "redirect:/user/create";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") String username){

        userService.delete(username);

        return "redirect:/user/create";
    }


}
