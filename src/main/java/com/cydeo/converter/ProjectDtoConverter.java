package com.cydeo.converter;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.service.ProjectService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
//@ConfigurationPropertiesBinding
public class ProjectDtoConverter implements Converter<String, ProjectDTO> {

    // injection
    private final ProjectService projectService;

    public ProjectDtoConverter(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public ProjectDTO convert(String sourceCode) {

        return projectService.findByProjectCode(sourceCode);
    }
}
