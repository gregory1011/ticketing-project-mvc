package com.cydeo.dto;

import com.cydeo.entity.BaseEntity;
import com.cydeo.enums.Status;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO extends BaseEntity {

    private ProjectDTO project;
    private UserDTO assignedEmployee;
    private String taskSubject, taskDetail;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate assignedDate;
    private Status taskStatus;

}
