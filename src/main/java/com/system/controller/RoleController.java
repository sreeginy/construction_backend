package com.system.controller;

import com.system.model.CommonResponse;

import com.system.model.Role;
import com.system.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping(path = "/role")

public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    //Add New Role
    @PostMapping(path = "/add")
    public @ResponseBody CommonResponse addNewRole(@RequestBody Role role ){
        Role roleDB = roleRepository.findByAccessLevel(role.getAccessLevel());

        if (roleDB == null){
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate= formatter.format(date);
            role.setCreatedAt(strDate);
            roleRepository.save(role);
            return CommonResponse.generateResponse(null,1000,"Success");
        }else {
            return CommonResponse.generateResponse(null,1001,"Product already exists");
        }
    }

    //List All Role
    @GetMapping(path = "/all")
    public @ResponseBody CommonResponse getAllRole() {
        return CommonResponse.generateResponse(roleRepository.findAll(),1000,"Success");

    }

    //Update Role
    @PostMapping(path = "/update/{id}")
    public CommonResponse updateRole(@RequestBody Role role, @PathVariable("id") Integer roleDBId){
        Role roleDB = roleRepository.findById(roleDBId).get();



        if (Objects.nonNull(role.getAccessLevel())
                && !"".equalsIgnoreCase(
                role.getAccessLevel())) {
            roleDB.setAccessLevel(
                    role.getAccessLevel());
        }

        if (Objects.nonNull(role.getEmail())
                && !"".equalsIgnoreCase(
                role.getEmail())) {
            roleDB.setEmail(
                    role.getEmail());
        }

        if (Objects.nonNull(role.getDescription())
                && !"".equalsIgnoreCase(
                role.getDescription())) {
            roleDB.setDescription(
                    role.getDescription());
        }

        return CommonResponse.generateResponse(roleRepository.save(roleDB),1000,"Success");

    }


    //Delete Role
    @GetMapping(path = "/delete/{id}")
    public @ResponseBody CommonResponse deleteRoleById(@PathVariable("id") Integer roleDBId){
        roleRepository.deleteById(roleDBId);

        return CommonResponse.generateResponse(null,1000,"Deleted Successfully");

    }
    

}
