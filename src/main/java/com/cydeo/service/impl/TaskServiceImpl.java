package com.cydeo.service.impl;

import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.TaskMapper;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.ProjectRepo;
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
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final ProjectRepo projectRepo;
    private final ProjectMapper projectMapper;


    @Override
    public List<TaskDTO> listAllTaskDTO() {

        List<Task> taskList = taskRepo.findAll();

        return taskList.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public TaskDTO findById(Long taskId) {

        Optional<Task> task = taskRepo.findById(taskId);

        return task.map(taskMapper::convertToDto).orElse(null);

    }

    @Override
    public void save(TaskDTO taskDTO) {

        taskDTO.setTaskStatus(Status.OPEN);
        taskDTO.setAssignedDate(LocalDate.now());
        Task task = taskMapper.convertToEntity(taskDTO);

//        User user = userRepo.findByUserName(taskDTO.getAssignedEmployee().getUserName());
//        Project project = projectRepo.findByProjectCode(taskDTO.getProject().getProjectCode());
//
//        Task task = taskMapper.convertToEntity(taskDTO);
//
//        task.setAssignedEmployee(user);
//        task.setProject(project);
//        task.setStatus(Status.OPEN);
//        task.setAssignedDate(LocalDate.now());

        taskRepo.save(task);
    }

    @Override
    public void update(TaskDTO taskDTO) {

        Task task = taskRepo.findTaskById(taskDTO.getId());
        Task convertedTask = taskMapper.convertToEntity(taskDTO);

        convertedTask.setId(task.getId());
        convertedTask.setStatus(task.getStatus());
        convertedTask.setAssignedDate(task.getAssignedDate());

        taskRepo.save(convertedTask);
    }

    @Override
    public void delete(Long id) {

        Task task = taskRepo.findById(id).get();
        task.setIsDeleted(true);
        taskRepo.save(task);

    }

}
