package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.ProjectRepo;
import com.cydeo.repository.UserRepo;
import com.cydeo.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {


    private final ProjectRepo projectRepo;
    private final ProjectMapper projectMapper;
    private final UserRepo userRepo;
    private final UserMapper userMapper;

    @Override
    public List<ProjectDTO> listAllProjects() {

        List<Project> listRepo = projectRepo.findAll();
        return listRepo.stream().map(projectMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public ProjectDTO findByProjectCode(String projectCode) {

        Project projectRep = projectRepo.findByProjectCode(projectCode);
        return projectMapper.convertToDTO(projectRep);
    }


    @Override
    public void save(ProjectDTO projectDTO) {

        projectDTO.setProjectStatus(Status.OPEN);
        Project project = projectMapper.convertToEntity(projectDTO);

        projectRepo.save(project);
    }


    @Override
    public void delete(String projectCode) {

        Project project = projectRepo.findByProjectCode(projectCode);
        project.setIsDeleted(true);
        projectRepo.save(project);
    }


    @Override
    public void complete(String projectCode) {

        Project project = projectRepo.findByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETED);

        projectRepo.save(project);
    }

    @Override
    public void update(ProjectDTO projectDto) {

        Project dbProject = projectRepo.findByProjectCode(projectDto.getProjectCode());
        Project convertedProject = projectMapper.convertToEntity(projectDto);

        convertedProject.setId(dbProject.getId());
        convertedProject.setProjectStatus(dbProject.getProjectStatus());

        projectRepo.save(convertedProject);
    }

}
