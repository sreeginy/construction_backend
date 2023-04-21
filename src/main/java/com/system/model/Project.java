package com.system.model;

import javax.persistence.*;

@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="project_name")
    private String projectName;

    @Column(name = "packages")
    private String packages;

    private String description;

    private Integer duration;

    private String location;
    private String clientName;

    private Double projectSqft;
    private Integer projectRoom;

    private String status;

    @Column(name="created_at")
    private String createdAt;

    public Project(){

    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Double getProjectSqft() {
        return projectSqft;
    }

    public void setProjectSqft(Double projectSqft) {
        this.projectSqft = projectSqft;
    }

    public Integer getProjectRoom() {
        return projectRoom;
    }

    public void setProjectRoom(Integer projectRoom) {
        this.projectRoom = projectRoom;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
