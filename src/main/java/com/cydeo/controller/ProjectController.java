package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.repository.ProjectRepo;
import com.cydeo.repository.UserRepo;
import com.cydeo.service.ProjectService;
import com.cydeo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/project")
@AllArgsConstructor
public class ProjectController {

    // inject dependency
    private final ProjectService projectService;
    private final UserService userService;
    private final UserRepo userRepo;
    private final ProjectRepo projectRepo;
    private final ProjectMapper projectMapper;


    @GetMapping("/create")
    public String projectCreate(ProjectDTO projectDTO, Model model){

        model.addAttribute("proJect", new ProjectDTO());
        model.addAttribute("projectS", projectService.listAllProjectDetails());
        model.addAttribute("managers", userService.listAllUsersByRole("Manager"));

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

        model.addAttribute("proJect", projectService.findByProjectCode(projectCode));
        model.addAttribute("projectS", projectService.listAllProjectDetails());
        model.addAttribute("managers", userService.listAllUsersByRole("manager"));

        return "/project/update";
    }

    @PostMapping("/edit")
    public String postProject(ProjectDTO project){

        projectService.update(project);

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
