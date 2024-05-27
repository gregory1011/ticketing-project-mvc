package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.ProjectRepo;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {


    private final ProjectRepo projectRepo;
    private final ProjectMapper projectMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final TaskService taskService;

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
        // the projectCode is already in DB
        // we set project code concat + id, this way it will be different code - unique in DB.
        // se when we crate a new
        project.setProjectCode(project.getProjectCode()+"-"+ project.getId());
        projectRepo.save(project);

        // we want to make delete all tasks associated with this project
        ProjectDTO projectDTO = projectMapper.convertToDTO(project);
        taskService.deleteByProject(projectDTO);
    }


    @Override
    public void complete(String projectCode) {

        Project project = projectRepo.findByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETE);

        projectRepo.save(project);

        // same logic like in delete method
        ProjectDTO dto = projectMapper.convertToDTO(project);
        taskService.completeByProject(dto);
    }

    @Override
    public void update(ProjectDTO projectDto) {

        Project dbProject = projectRepo.findByProjectCode(projectDto.getProjectCode());
        Project convertedProject = projectMapper.convertToEntity(projectDto);

        convertedProject.setId(dbProject.getId());
        convertedProject.setProjectStatus(dbProject.getProjectStatus());

        projectRepo.save(convertedProject);
    }

    @Override
    public List<ProjectDTO> listAllProjectDetails() {

        // whoever user is logged in the system - SecurityContextHolder will bring the name
        String usernameAuthenticated = SecurityContextHolder.getContext().getAuthentication().getName();

        UserDTO currentUserDto = userService.findUserByUserName(usernameAuthenticated);

        User user = userMapper.convertToEntity(currentUserDto);

        List<Project> list = projectRepo.findAllByAssignedManager(user);



        // get the list and stream it the use map to work through
        return list.stream().map(project -> {

            ProjectDTO dto = projectMapper.convertToDTO(project);

            // setting up unfinished tasks
            dto.setUnfinishedTaskCounts(taskService.totalNonCompletedTasks(project.getProjectCode()));

            // setting up completed tasks
            dto.setCompletedTaskCounts(taskService.totalCompletedTasks(project.getProjectCode()));
            return dto;

        }).collect(Collectors.toList());

    }

    @Override
    public List<ProjectDTO> readAllByAssignedManager(User assignedManager) {

        List<Project> list = projectRepo.findAllByAssignedManager(assignedManager);
        return list.stream().map(projectMapper::convertToDTO).collect(Collectors.toList());
    }

}
