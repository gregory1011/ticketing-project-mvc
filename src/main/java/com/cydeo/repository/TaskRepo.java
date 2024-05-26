package com.cydeo.repository;

import com.cydeo.entity.Project;
import com.cydeo.entity.Task;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;


public interface TaskRepo extends JpaRepository<Task, Long> {

//    Task findTaskById(Long id);

    // JPQL query
    @Query("SELECT count( t ) from Task t where t.project.projectCode = ?1 and t.taskStatus <> 'COMPLETE'")
    int totalNonCompletedTasks(String projectCode);

    // Native query
    @Query(value = "select count(*) from tasks t join  projects p on t.project_id = p.id where p.project_code =?1 and t.task_status ='COMPLETE'", nativeQuery = true)
    int totalCompletedTasks(String projectCode);

    List<Task> findAllByProject(Project project);

    List<Task> findAllByTaskStatusIsNotAndAssignedEmployee(Status status, User loggedInUser);

    List<Task> findAllByTaskStatusAndAssignedEmployee(Status status, User loggedInUser);

    List<Task> findAllByAssignedEmployee(User assignedEmployee);



//    @Transactional
//    void deleteTaskByTaskId(Long taskId);
}
