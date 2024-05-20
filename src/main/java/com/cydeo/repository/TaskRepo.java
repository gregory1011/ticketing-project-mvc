package com.cydeo.repository;

import com.cydeo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;


public interface TaskRepo extends JpaRepository<Task, Long> {

    Task findTaskById(Long id);

//    @Transactional
//    void deleteTaskByTaskId(Long taskId);
}
