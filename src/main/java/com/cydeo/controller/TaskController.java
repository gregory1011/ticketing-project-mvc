package com.cydeo.controller;

import com.cydeo.dto.TaskDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
        model.addAttribute("employees", userService.listAllUsersByRole("employee"));
        model.addAttribute("taskS", taskService.listAllTaskDTO());

        return "/task/create";
    }

    @PostMapping("/create")
    public String taskCreated(@Valid @ModelAttribute("Task") TaskDTO Task, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){

            model.addAttribute("projects", projectService.listAllProjects());
            model.addAttribute("employees", userService.listAllUsersByRole("employee"));
            model.addAttribute("taskS", taskService.listAllTaskDTO());

            return "/task/create";
        }

        taskService.save(Task);
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
        model.addAttribute("employees", userService.listAllUsersByRole("employee"));
        model.addAttribute("taskS", taskService.listAllTaskDTO());

        return "/task/update";
    }

    @PostMapping("/editTask/{id}")
    public String updateTask(@Valid @PathVariable("id") Long id, TaskDTO task, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            model.addAttribute("projects", projectService.listAllProjects());
            model.addAttribute("employees", userService.listAllUsersByRole("employee"));
            model.addAttribute("taskS", taskService.listAllTaskDTO());

            return "/task/update";
        }

        task.setId(id);
        taskService.update(task);

        return "redirect:/task/create";
    }

//    @PostMapping("/editTask/{taskId}") // this variable taskId is from TaskDTO and sets it auto.
//    public String updateTask(TaskDTO task){ // task variable acts like a depend injection and sets taskId
//        // task.taskId = taskId;
//        taskService.update(task);
//
//        return "redirect:/task/create";
//    }

//    // ------------  Employee task pending --------------//

    @GetMapping("/employee/pending-tasks")
    public String getPendingTasks(Model model){

        model.addAttribute("tasks", taskService.listAllTaskByStatusIsNot(Status.COMPLETE));

        return "/task/pending-tasks";
    }

    @GetMapping("/employee/edit/{id}")
    public String employeeEditTask(@PathVariable("id") Long id, Model model){

        model.addAttribute("task", taskService.findById(id));
//        model.addAttribute("employees", userService.findEmployee());
//        model.addAttribute("projects", projectService.listAllAllNonCompletedProjects());
        model.addAttribute("tasks", taskService.listAllTaskByStatusIsNot(Status.COMPLETE));
        model.addAttribute("statuses", Status.values());

        return "/task/status-update";
    }

//    @PostMapping("/employee/update/{taskId}")
//    public String employeeUpdateTask(TaskDTO taskDTO){
//
//        taskService.updateStatus(taskDTO);
//        return "redirect:/task/employee/pending-task";
//    }

    @PostMapping("/employee/update/{id}")
    public String empUpdateTask(@Valid @ModelAttribute("task") TaskDTO task, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            model.addAttribute("tasks", taskService.listAllTaskByStatusIsNot(Status.COMPLETE));
            model.addAttribute("statuses", Status.values());

            return "/task/status-update";
        }

        taskService.updateStatus(task);

        return "redirect:/task/employee/pending-tasks";
    }


    // -------------  Employee archive ------------//


    @GetMapping("/employee/archive")
    public String getArchive(Model model){

        model.addAttribute("task", taskService.listAllTaskByStatus(Status.COMPLETE));

        return "/task/archive";
    }




}
