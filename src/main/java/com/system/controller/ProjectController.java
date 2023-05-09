package com.system.controller;

import com.system.model.CommonResponse;
import com.system.model.Product;
import com.system.model.Project;
import com.system.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping(path = "/project")
public class ProjectController {


    @Autowired
    private ProjectRepository projectRepository;

    //Add New Project
    @PostMapping(path = "/add")
    public @ResponseBody CommonResponse addNewProject(@RequestBody Project project){
        Project projectDB = projectRepository.findByProjectName(project.getProjectName());

        if (projectDB == null){
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate= formatter.format(date);
            project.setCreatedAt(strDate);
            projectRepository.save(project);
            return CommonResponse.generateResponse(null,1000,"Success");
        }else {
            return CommonResponse.generateResponse(null,1001,"Project already exists");
        }
    }


    //List All Project
    @GetMapping(path = "/all")
    public @ResponseBody CommonResponse getAllProjects() {
        return CommonResponse.generateResponse(projectRepository.findAll(),1000,"Success");

    }
    
    //Update Project
    @PostMapping(path = "/update/{id}")
    public CommonResponse updateProject(@RequestBody Project project,@PathVariable("id") Integer projectId){
        Project projectDB = projectRepository.findById(projectId).get();


        if (Objects.nonNull(project.getProjectName())
                && !"".equalsIgnoreCase(
                project.getProjectName())) {
            projectDB.setProjectName(
                    project.getProjectName());
        }

        if (Objects.nonNull(project.getPackages())
                && !"".equalsIgnoreCase(
                project.getPackages())) {
            projectDB.setPackages(
                    project.getPackages());
        }

        if (Objects.nonNull(project.getDescription())
                && !"".equalsIgnoreCase(
                project.getDescription())) {
            projectDB.setDescription(
                    project.getDescription());
        }

        if (Objects.nonNull(project.getDuration())
                && !"".equalsIgnoreCase(
                project.getDuration().toString())) {
            projectDB.setDuration(
                    project.getDuration());
        }

        if (Objects.nonNull(project.getLocation())
                && !"".equalsIgnoreCase(
                project.getLocation())) {
            projectDB.setLocation(
                    project.getLocation());
        }

        if (Objects.nonNull(project.getClientName())
                && !"".equalsIgnoreCase(
                project.getClientName())) {
            projectDB.setClientName(
                    project.getClientName());
        }

        if (Objects.nonNull(project.getProjectSqft())
                && !"".equalsIgnoreCase(
                project.getProjectSqft().toString())) {
            projectDB.setProjectSqft(
                    project.getProjectSqft());
        }

        if (Objects.nonNull(project.getProjectRoom())
                && !"".equalsIgnoreCase(
                project.getProjectRoom().toString())) {
            projectDB.setProjectRoom(
                    project.getProjectRoom());
        }

        if (Objects.nonNull(project.getStatus())
                && !"".equalsIgnoreCase(
                project.getStatus())) {
            projectDB.setStatus(
                    project.getStatus());
        }

        return CommonResponse.generateResponse(projectRepository.save(projectDB),1000,"Success!!!");
//        return CommonResponse.generateResponse(null,1000,"Updated Successfully");

    }

    //Delete Project
    @GetMapping(path = "/delete/{id}")
    public @ResponseBody CommonResponse deleteProjectById(@PathVariable("id") Integer projectId){
        projectRepository.deleteById(projectId);

        return CommonResponse.generateResponse(null,1000,"Deleted Successfully");
    }

    //countable
    @GetMapping(path = "/count")
    public @ResponseBody long getCount(){
        return projectRepository.count();
    }
    
}
