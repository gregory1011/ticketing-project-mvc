package com.cydeo.entity;

import com.cydeo.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "projects")
@NoArgsConstructor
@Getter
@Setter
@Where(clause = "is_deleted=false")
public class Project extends BaseEntity{

    private String projectName;

    @Column(unique = true)  // this is the validation for unique project code. NO double
    private String projectCode;

    private LocalDate startDate;
    private LocalDate endDate;
    private String projectDetail;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "manager_id")
    private User assignedManager;

    @Enumerated(EnumType.STRING)
    private Status projectStatus;

    @Override
    public String toString() {
        return "Project{" +
                "projectName='" + projectName + '\'' +
                ", projectCode='" + projectCode + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", projectDetail='" + projectDetail + '\'' +
                ", projectStatus=" + projectStatus +
                '}';
    }
}
