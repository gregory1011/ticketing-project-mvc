package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl extends AbstractMapService<ProjectDTO, String > implements ProjectService {


    private final TaskService taskService;

    public ProjectServiceImpl(TaskService taskService) {
        this.taskService = taskService;
    }

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

    @Override
    public List<ProjectDTO> findAllNonCompletedProjects() {
        return findAll().stream()
                .filter(project -> ! project.getProjectStatus().equals(Status.COMPLETED))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> getCountedListOfProjectDTO(UserDTO manager) {

        // one goal - built that ProjectDTO with All argument constructor
        List<ProjectDTO> projectList =
                findAll()
                .stream()
                .filter(pro -> pro.getAssignedManager().equals(manager))
                // use this map to built ProjectDTo object with all instances from tht class
                .map(projectDTO -> {
//                    int completedTaskCounts = 6;
//                    int unfinishedTaskCounts = 8;

                    // how to find those 2 variables, they must be dynamic
                    List<TaskDTO> taskList = taskService.findTaskByManager(manager);

                    // by looking at the status we can determine how many are completed and how many are not
                    int completedTaskCounts = (int) taskList.stream(). // we need 2 things p-> belongs to the project that I'm looking for
                            // second thing is to find completed
                            filter(p -> p.getProject().equals(projectDTO) && p.getTaskStatus() == Status.COMPLETED)
                            .count();

                    int unfinishedTaskCounts = (int) taskList.stream() // we pars long to int - > (int)
                            .filter(p -> p.getProject().equals(projectDTO) && p.getTaskStatus() != Status.COMPLETED)
                            .count();

                    projectDTO.setCompletedTaskCounts(completedTaskCounts);
                    projectDTO.setUnfinishedTaskCounts(unfinishedTaskCounts);


                    return projectDTO;

                }).collect(Collectors.toList());


        return projectList;
    }
}
