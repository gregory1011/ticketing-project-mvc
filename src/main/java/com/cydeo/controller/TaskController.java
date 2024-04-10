package com.cydeo.controller;

import com.cydeo.dto.TaskDTO;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;
    private final ProjectService projectService;
    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService, ProjectService projectService) {
        this.taskService = taskService;
        this.userService = userService;
        this.projectService = projectService;
    }

    @GetMapping("/create")
    public String createTask(Model model){

        model.addAttribute("Task", new TaskDTO());
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("employees", userService.findEmployee());
        model.addAttribute("taskS", taskService.findAll());

        return "/task/create";
    }

    @PostMapping("/create")
    public String taskCreated(TaskDTO task){

        taskService.save(task);
        return "redirect:/task/create";
    }

    @GetMapping("/delete/{taskId}")
    public String deleteTask(@PathVariable("taskId") Long taskId) {

        taskService.deleteById(taskId);

        return "redirect:/task/create";
    }

    @GetMapping("/editTask/{taskId}")
    public String editTask(@PathVariable("taskId") Long taskId, Model model){

        model.addAttribute("Task", taskService.findById(taskId));
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("employees", userService.findEmployee());
        model.addAttribute("taskS", taskService.findAll());

        return "/task/update";
    }

//    @PostMapping("/editTask/{id}")
//    public String updateTask(@PathVariable("id") Long taskId, TaskDTO task){
//
//        task.setTaskId(taskId);
//        taskService.update(task);
//
//        return "redirect:/task/create";
//    }

    @PostMapping("/editTask/{taskId}") // this variable taskId is from TaskDTO and sets it auto.
    public String updateTask(TaskDTO task){ // task variable acts like a depend injection and sets taskId
        // task.taskId = taskId;
        taskService.update(task);

        return "redirect:/task/create";
    }






}
