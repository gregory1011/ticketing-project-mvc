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

        List<Project> repoAll = projectRepo.findAll();

        return repoAll.stream().map(projectMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public ProjectDTO findByProjectCode(String projectCode) {

        Project projectRep = projectRepo.findByProjectCode(projectCode);

        return projectMapper.convertToDTO(projectRep);
    }


    @Override
    public void save(ProjectDTO projectDTO) {

        User manger = userRepo.findByUserName(projectDTO.getAssignedManager().getUserName());

        Project project = projectMapper.convertToEntity(projectDTO);
        project.setProjectStatus(Status.OPEN);

        project.setAssignedManager(manger);

        projectRepo.save(project);
    }

    @Override
    public void deleteByProjectCode(String projectCode) {

        Project project = projectRepo.findByProjectCode(projectCode);
        project.setIsDeleted(true);
        projectRepo.delete(project);
    }

    @Override
    public void delete(String projectCode) {

        Project projectEntity = projectRepo.findByProjectCode(projectCode);
        projectEntity.setIsDeleted(true);

        projectRepo.save(projectEntity);
    }


    @Override
    public void complete(ProjectDTO projectDto) {

        Project dbProject = projectRepo.findByProjectCode(projectDto.getProjectCode());

        dbProject.setProjectStatus(Status.COMPLETED);

        projectRepo.save(dbProject);
    }

    @Override
    public void update(ProjectDTO projectDto) {

        Project dbProject = projectRepo.findByProjectCode(projectDto.getProjectCode());

        if (projectDto.getProjectStatus() == null){
            projectDto.setProjectStatus(dbProject.getProjectStatus());
        }

        Long id = dbProject.getId();
        UserDTO manager = projectDto.getAssignedManager();

        Project convertedProject = projectMapper.convertToEntity(projectDto);
        User assignerManger = userMapper.convertToEntity(manager);

        convertedProject.setId(id);
        convertedProject.setAssignedManager(assignerManger);

        projectRepo.save(convertedProject);

    }

}
