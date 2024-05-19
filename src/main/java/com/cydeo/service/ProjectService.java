package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {

    List<ProjectDTO> listAllProjects();

    ProjectDTO findByProjectCode(String projectCode);

    void save(ProjectDTO project);

    void deleteByProjectCode(String projectCode);
    void delete(String sourceCode);

    void complete(ProjectDTO projectDto);

    void update(ProjectDTO project);

}
