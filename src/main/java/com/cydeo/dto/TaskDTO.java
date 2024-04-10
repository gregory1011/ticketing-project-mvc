package com.cydeo.dto;

import com.cydeo.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class TaskDTO {

    private Long taskId; // I will need some uniq id to be able to work and retrive data DB
    private ProjectDTO project;
    private UserDTO assignedEmployee;
    private String taskSubject, taskDetail;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate assignedDate;
    private Status taskStatus;

    // all argument const without Long taskID,  because this field will be created by DB Postgre
    public TaskDTO(ProjectDTO project, UserDTO assignedEmployee, String taskSubject, String taskDetail, LocalDate assignedDate, Status taskStatus) {
        this.taskId = UUID.randomUUID().getMostSignificantBits();
        this.project = project;
        this.assignedEmployee = assignedEmployee;
        this.taskSubject = taskSubject;
        this.taskDetail = taskDetail;
        this.assignedDate = assignedDate;
        this.taskStatus = taskStatus;
    }
}
