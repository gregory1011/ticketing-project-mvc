package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.TaskMapper;
import com.cydeo.repository.TaskRepo;
import com.cydeo.repository.UserRepo;
import com.cydeo.service.TaskService;
import lombok.AllArgsConstructor;
import com.cydeo.entity.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepo taskRepo;
    private final TaskMapper taskMapper;
    private final ProjectMapper projectMapper;
    private final UserRepo userRepo;


    @Override
    public List<TaskDTO> listAllTaskDTO() {

        List<Task> taskList = taskRepo.findAll();

        return taskList.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public TaskDTO findById(Long taskId) {

        Optional<Task> task = taskRepo.findById(taskId);

//        if (task.isPresent()){
//            return taskMapper.convertToDto(task.get());
//        }else {
//            return null;
//        }

        return task.map(taskMapper::convertToDto).orElse(null);
    }

    @Override
    public void save(TaskDTO taskDTO) {

        taskDTO.setTaskStatus(Status.OPEN);
        taskDTO.setAssignedDate(LocalDate.now());
        Task task = taskMapper.convertToEntity(taskDTO);

        taskRepo.save(task);
    }

    @Override
    public void update(TaskDTO taskDTO) {

        // get task from DB
        Optional<Task> task = taskRepo.findById(taskDTO.getId());
        // convert UI task to Entity
        Task convertedTask = taskMapper.convertToEntity(taskDTO);

        // validate isPresent from Optional
        if(task.isPresent()){
            // then setID
            convertedTask.setId(task.get().getId());
            //update with turnery statment if Status null then get taskEntity Status else get taskDto status
            convertedTask.setTaskStatus(taskDTO.getTaskStatus() == null ? task.get().getTaskStatus() : taskDTO.getTaskStatus());
            // update  date
            convertedTask.setAssignedDate(taskDTO.getAssignedDate());
            //save convertedTask in DB
            taskRepo.save(convertedTask);
        }
    }

    @Override
    public void delete(Long id) {

        Optional<Task> task = taskRepo.findById(id);

        if (task.isPresent()){
            task.get().setIsDeleted(true);
            taskRepo.save(task.get());
        }
    }

    @Override
    public int totalNonCompletedTasks(String projectCode) {

        return taskRepo.totalNonCompletedTasks(projectCode);
    }

    @Override
    public int totalCompletedTasks(String projectCode) {
        return taskRepo.totalCompletedTasks(projectCode);
    }

    @Override
    public void deleteByProject(ProjectDTO dto) {

        // create a new method to list taskDTO's by providing projectDTO as param
        List<TaskDTO> listTask = listAllByProject(dto);

        // this line of code iterates through the list of tasksDTO and deletes each based on taskID
        // functional iteration
        listTask.forEach(task -> delete(task.getId()));
    }

    @Override
    public void completeByProject(ProjectDTO dto) {
        // we get all tasks
        List<TaskDTO> list = listAllByProject(dto);

        // we iterate through each task
        list.forEach(taskDTO -> {
            //nd setting status to complete.
            taskDTO.setTaskStatus(Status.COMPLETE);
            // we use update method that we override from taskService
            update(taskDTO);
        });
    }

    @Override
    public List<TaskDTO> listAllTaskByStatusIsNot(Status status) {

        User loggedInUser = userRepo.findByUserName("john@employee.com");
        List<Task> list = taskRepo.findAllByTaskStatusIsNotAndAssignedEmployee(status, loggedInUser);

        return list.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> listAllTaskByStatus(Status status) {

        User loggedInUser = userRepo.findByUserName("john@employee.com");
        List<Task> list = taskRepo.findAllByTaskStatusAndAssignedEmployee(status, loggedInUser);

        return list.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void updateStatus(TaskDTO dto) {

        // this update method we don't need to update ID since the data is coming from DB with id
        Optional<Task> task = taskRepo.findById(dto.getId());

        if (task.isPresent()){
            task.get().setTaskStatus(dto.getTaskStatus());
            taskRepo.save(task.get());
        }
    }

    @Override
    public List<TaskDTO> readAllAssignedEmployee(User assignedEmployee) {

        List<Task> taskList = taskRepo.findAllByAssignedEmployee(assignedEmployee);
        return taskList.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    private List<TaskDTO> listAllByProject(ProjectDTO projectDTO) {

        // convert to projectDTO to Entity
        Project project = projectMapper.convertToEntity(projectDTO);

        // retrieve all tasks from DB based by projectEntity
        List<Task> taskList = taskRepo.findAllByProject(project);

        // stream the list of taskEntity :: convert to DTO and collect to list then return to the method above
        return taskList.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }


}
