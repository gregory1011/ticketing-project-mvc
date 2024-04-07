package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.service.ProjectService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("managers", userService.findManager());

        return "/project/create";
    }

    @PostMapping("/create")
    public String projectPost(ProjectDTO project){

        // save the info from the UI form
        projectService.save(project);

        return "redirect:/project/create";
    }

    @GetMapping("delete/{projectCode}")
    public String deleteProject(@PathVariable("projectCode") String projectCode){

        projectService.deleteById(projectCode);

        return "redirect:/project/create";
    }

    @GetMapping("/complete/{projectCode}")
    public String completeProject(@PathVariable("projectCode") String projectCode){

        projectService.complete(projectService.findById(projectCode));
        return "redirect:/project/create";
    }

    @GetMapping("/edit/{projectCode}")
    public String editProject(@PathVariable("projectCode") String projectCode, Model model){

        model.addAttribute("proJect", projectService.findById(projectCode));
        model.addAttribute("projectS", projectService.findAll());
        model.addAttribute("managers", userService.findManager());

        return "/project/update";
    }

    @PostMapping("/edit")
    public String postProject(ProjectDTO project){

        // save updated project in projectService -> HashMap
        projectService.update(project);

        return "redirect:/project/create";
    }




}
