package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl extends AbstractMapService<ProjectDTO, String > implements ProjectService {
    @Override
    public ProjectDTO save(ProjectDTO object) {

        //this example is not efficient due to the fact that is always open, but we need to set it when we create new project only.
       // object.setProjectStatus(Status.OPEN);

        // everytime we create a new project we save it, and status must be Open
        if (object.getProjectStatus() == null){
            object.setProjectStatus(Status.OPEN);
        }

        return super.save(object.getProjectCode(), object);
    }

    @Override
    public List<ProjectDTO> findAll() {
        return super.findAll();
    }

    @Override
    public void update(ProjectDTO object) {

        // when we update the existing project the status code we cannot update in the UI
        // for this reason we need to collect first the status code from DB where it was saved
        ProjectDTO projecT = findById(object.getProjectCode());

        // then we use if statement to setProjectStatus
        if (object.getProjectStatus() == null) {
            object.setProjectStatus(projecT.getProjectStatus());
        }

        super.update(object.getProjectCode(), object);
    }

    @Override
    public void deleteById(String id) {
        super.deleteById(id);
    }

    @Override
    public ProjectDTO findById(String id) {
        return super.findById(id);
    }

    @Override
    public void complete(ProjectDTO project) {
        // get this project and set Status = Completed
        project.setProjectStatus(Status.COMPLETED);

        // save the project
        super.save(project.getProjectCode(), project);
    }
}
