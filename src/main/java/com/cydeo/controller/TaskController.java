package com.cydeo.controller;

import com.cydeo.dto.TaskDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;
    private final ProjectService projectService;
    private final UserService userService;



    @GetMapping("/create")
    public String createTask(Model model){

        model.addAttribute("Task", new TaskDTO());
        model.addAttribute("projects", projectService.listAllProjects());
        model.addAttribute("employees", userService.listAllUsers());
        model.addAttribute("taskS", taskService.listAllTaskDTO());

        return "/task/create";
    }

    @PostMapping("/create")
    public String taskCreated(TaskDTO task){

        taskService.save(task);
        return "redirect:/task/create";
    }

    @GetMapping("/delete/{taskId}")
    public String deleteTask(@PathVariable("taskId") Long taskId) {

        taskService.delete(taskId);

        return "redirect:/task/create";
    }

    @GetMapping("/editTask/{taskId}")
    public String editTask(@PathVariable("taskId") Long taskId, Model model){

        model.addAttribute("Task", taskService.findById(taskId));
        model.addAttribute("projects", projectService.listAllProjects());
        model.addAttribute("employees", userService.listAllUsers());
        model.addAttribute("taskS", taskService.listAllTaskDTO());

        return "/task/update";
    }

    @PostMapping("/editTask/{id}")
    public String updateTask(@PathVariable("id") Long id, TaskDTO task){

        task.setId(id);
        taskService.update(task);

        return "redirect:/task/create";
    }
//
//    @PostMapping("/editTask/{taskId}") // this variable taskId is from TaskDTO and sets it auto.
//    public String updateTask(TaskDTO task){ // task variable acts like a depend injection and sets taskId
//        // task.taskId = taskId;
//        taskService.update(task);
//
//        return "redirect:/task/create";
//    }
//
//    // ------------  Employee task pending --------------//
//
//    @GetMapping("/employee/pendingTasks")
//    public String getPendingTasks(Model model){
//
//        model.addAttribute("tasks", taskService.findTaskByStatusIsNot(Status.COMPLETED));
//
//        return "/task/pending-tasks";
//    }
//
//    @GetMapping("/employee/edit/{id}")
//    public String employeeEditTask(@PathVariable("id") Long id, Model model){
//
//        model.addAttribute("task", taskService.findById(id));
//        model.addAttribute("employees", userService.findEmployee());
//        model.addAttribute("projects", projectService.findAllNonCompletedProjects());
//        model.addAttribute("tasks", taskService.findTaskByStatusIsNot(Status.COMPLETED));
//        model.addAttribute("statuses", Status.values());
//
//        return "/task/status-update";
//    }
//
////    @PostMapping("/employee/update/{taskId}")
////    public String employeeUpdateTask(TaskDTO taskDTO){
////
////        taskService.updateStatus(taskDTO);
////        return "redirect:/task/employee/pending-task";
////    }
//
//    @PostMapping("/employee/update/{id}")
//    public String empUpdateTask(@PathVariable("id") Long id, TaskDTO task){
//
//        task.setTaskId(id);
//        taskService.updateStatus(task);
//
//        return "redirect:/task/employee/pendingTasks";
//    }
//
//
//    // -------------  Employee archive ------------//
//
//
//    @GetMapping("/employee/archive")
//    public String getArchive(Model model){
//
//        model.addAttribute("task", taskService.findTaskByStatus(Status.COMPLETED));
//
//        return "/task/archive";
//    }




}
