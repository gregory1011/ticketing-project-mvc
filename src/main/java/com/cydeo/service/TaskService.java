package com.cydeo.service;

import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;

import java.util.List;

public interface TaskService extends CrudService<TaskDTO, Long>{

    List<TaskDTO> findTaskByManager(UserDTO manager);
    List<TaskDTO> findTaskByStatus(Status status);
    List<TaskDTO> findTaskByStatusIsNot(Status status);
    void updateStatus(TaskDTO task);
}
