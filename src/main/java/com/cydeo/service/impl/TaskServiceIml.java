package com.cydeo.service.impl;

import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskServiceIml extends AbstractMapService<TaskDTO, Long> implements TaskService {


    @Override
    public TaskDTO save(TaskDTO object) {

        if (object.getTaskStatus() == null){
            object.setTaskStatus(Status.OPEN);
        }

        if (object.getAssignedDate() == null){
            object.setAssignedDate(LocalDate.now());
        }

        if (object.getTaskId() == null){
            object.setTaskId(UUID.randomUUID().getMostSignificantBits());
        }

        return super.save(object.getTaskId(), object);
    }

    @Override
    public List<TaskDTO> findAll() {
        return super.findAll();
    }

    @Override
    public void update(TaskDTO object) {

        TaskDTO foundTask = findById((object.getTaskId()));

        object.setTaskStatus(foundTask.getTaskStatus());
        object.setAssignedDate(foundTask.getAssignedDate());
        super.update(object.getTaskId(), object);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public TaskDTO findById(Long id) {
        return super.findById(id);
    }

    @Override
    public List<TaskDTO> findTaskByManager(UserDTO manager) {
        return findAll().stream()
                .filter(task -> task.getProject().getAssignedManager().equals(manager))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findTaskByStatus(Status status) {
        return findAll().stream()
                .filter(task -> task.getTaskStatus().equals(Status.COMPLETED))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findTaskByStatusIsNot(Status status) {
        return findAll().stream()
                .filter(task -> ! task.getTaskStatus().equals(Status.COMPLETED))
                .collect(Collectors.toList());
    }

    @Override
    public void updateStatus(TaskDTO task) {
        findById(task.getTaskId())
                .setTaskStatus(task.getTaskStatus());
        update(task);
    }

}
