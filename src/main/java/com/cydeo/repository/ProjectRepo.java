package com.cydeo.repository;

import com.cydeo.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface ProjectRepo extends JpaRepository<Project, Long> {

    Project findByProjectCode(String sourceID);

    @Transactional
    void deleteProjectByProjectCode(String sourceID);

}
