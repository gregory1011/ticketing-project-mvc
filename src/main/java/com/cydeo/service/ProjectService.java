package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {

    List<ProjectDTO> listAllProjects();

    ProjectDTO findByProjectCode(String projectCode);

    void save(ProjectDTO project);

    void delete(String sourceCode);

    void complete(String projectCode);

    void update(ProjectDTO project);

}
