package com.system.repository;


import com.system.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository  extends JpaRepository<Project,Integer> {

    Project findByProjectName(String name);

}
