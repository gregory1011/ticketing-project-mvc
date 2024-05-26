package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;

import java.util.List;

public interface TaskService {

    List<TaskDTO> listAllTaskDTO();
    TaskDTO findById(Long id);
    void save(TaskDTO dto);
    void update(TaskDTO dto);
    void delete(Long id);

    int totalNonCompletedTasks(String projectCode);

    int totalCompletedTasks(String projectCode);

    void deleteByProject(ProjectDTO dto);

    void completeByProject(ProjectDTO dto);

    List<TaskDTO> listAllTaskByStatusIsNot(Status status);

    List<TaskDTO> listAllTaskByStatus(Status status);

    void updateStatus(TaskDTO task);

    List<TaskDTO> readAllAssignedEmployee(User assignedEmployee);
}
