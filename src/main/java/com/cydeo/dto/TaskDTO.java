package com.cydeo.dto;

import com.cydeo.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    private Long taskId; // I will need some uniq id to be able to work and retrive data DB
    private ProjectDTO project;
    private UserDTO assignedEmployee;
    private String taskSubject, taskDetail;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate assignedDate;
    private Status taskStatus;
}
