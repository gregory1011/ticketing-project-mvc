package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.service.ProjectService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/project")
public class ProjectController {

    // inject dependency
    private final ProjectService projectService;
    private final UserService userService;

    // inject through constructor (or field, or setter ...)
    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String projectCreate(ProjectDTO projectDTO, Model model){

        model.addAttribute("proJect", new ProjectDTO());
        model.addAttribute("projectS", projectService.findAll());
        model.addAttribute("managers", userService.findAll());

        return "/project/create";
    }

    @PostMapping("/create")
    public String projectPost(ProjectDTO project){

        // save the info from the UI form
        projectService.save(project);

        return "redirect:/project/create";
    }


}
