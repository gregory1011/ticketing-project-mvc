package com.cydeo.mapper;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.Project;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProjectMapper {

    private final ModelMapper modelMapper;

    public ProjectDTO convertToDTO(Project project){
        return modelMapper.map(project, ProjectDTO.class);
    }

    public Project convertToEntity(ProjectDTO projectDTO){
        return modelMapper.map(projectDTO, Project.class);
    }

}
