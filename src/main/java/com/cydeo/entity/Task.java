package com.cydeo.entity;

import com.cydeo.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted=false")
public class Task extends BaseEntity{

    private String taskSubject;
    private String taskDetail;

    @Column(columnDefinition = "DATE")
    private LocalDate assignedDate;

    @Enumerated(EnumType.STRING)
    private Status taskStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    private User assignedEmployee;


}
