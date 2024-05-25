package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.User;

import java.util.List;

public interface ProjectService {

    List<ProjectDTO> listAllProjects();

    ProjectDTO findByProjectCode(String projectCode);

    void save(ProjectDTO project);

    void delete(String sourceCode);

    void complete(String projectCode);

    void update(ProjectDTO project);

    List<ProjectDTO> listAllProjectDetails();

    List<ProjectDTO> readAllByAssignedManager(User assignedManager);

}
