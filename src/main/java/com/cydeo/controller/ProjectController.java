package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.service.ProjectService;
import com.cydeo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/project")
@AllArgsConstructor
public class ProjectController {

    // inject dependency
    private final ProjectService projectService;
    private final UserService userService;


    @GetMapping("/create")
    public String projectCreate(Model model){

        model.addAttribute("projectDTO", new ProjectDTO());
        model.addAttribute("projectS", projectService.listAllProjectDetails());
        model.addAttribute("managers", userService.listAllUsersByRole("Manager"));

        return "/project/create";
    }

    @PostMapping("/create")
    public String projectPost(@Valid @ModelAttribute("projectDTO") ProjectDTO projectDTO, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            model.addAttribute("projectS", projectService.listAllProjectDetails());
            model.addAttribute("managers", userService.listAllUsersByRole("Manager"));

            return "/project/create";
        }

        // save the info from the UI form
        projectService.save(projectDTO);

        return "redirect:/project/create";
    }

    @GetMapping("delete/{projectCode}")
    public String deleteProject(@PathVariable("projectCode") String projectCode){

        projectService.delete(projectCode);

        return "redirect:/project/create";
    }

    @GetMapping("/complete/{projectCode}")
    public String completeProject(@PathVariable("projectCode") String projectCode){

        projectService.complete(projectCode);

        return "redirect:/project/create";
    }

    @GetMapping("/edit/{projectCode}")
    public String editProject(@PathVariable("projectCode") String projectCode, Model model){

        model.addAttribute("projectDTO", projectService.findByProjectCode(projectCode));
        model.addAttribute("projectS", projectService.listAllProjectDetails());
        model.addAttribute("managers", userService.listAllUsersByRole("manager"));

        return "/project/update";
    }

    @PostMapping("/edit")
    public String postProject(@Valid @ModelAttribute("projectDTO") ProjectDTO projectDTO, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            model.addAttribute("projectS", projectService.listAllProjectDetails());
            model.addAttribute("managers", userService.listAllUsersByRole("manager"));

            return "/project/update";
        }

        projectService.update(projectDTO);

        return "redirect:/project/create";
    }

    @GetMapping("/manager/project-status")
    public String getProjectByManager(Model model){


//        UserDTO manager = userService.findUserByUserName("harold@manager.com");


        List<ProjectDTO> projects = projectService.listAllProjectDetails();

        model.addAttribute("projects", projects);

        return "/manager/project-status";
    }


    @GetMapping("/manager/complete/{projectCode}")
    public String managerCompleteProject(@PathVariable("projectCode") String projectCode){

        projectService.complete(projectCode);

        return "redirect:/project/manager/project-status";
    }


}
