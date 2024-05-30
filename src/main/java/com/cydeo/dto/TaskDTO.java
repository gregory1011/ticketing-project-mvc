package com.cydeo.dto;

import com.cydeo.entity.BaseEntity;
import com.cydeo.enums.Status;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO extends BaseEntity {

    @NotNull
    private ProjectDTO project;
    @NotNull
    private UserDTO assignedEmployee;
    @NotBlank
    private String taskSubject;
    @NotBlank
    private String taskDetail;

    private Status taskStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate assignedDate;

}
