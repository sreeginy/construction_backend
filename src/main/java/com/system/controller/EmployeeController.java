package com.system.controller;

import com.system.model.CommonResponse;
import com.system.model.Employee;
import com.system.model.Product;
import com.system.model.Project;
import com.system.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    //Add New Employee

    @PostMapping(path = "/add")
    public @ResponseBody CommonResponse addNewEmployee(@RequestBody Employee employee){
        Employee employeeDB = employeeRepository.findByEmployeeName(employee.getEmployeeName());

        if (employeeDB == null){
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate= formatter.format(date);
            employee.setCreatedAt(strDate);
            employeeRepository.save(employee);
            return CommonResponse.generateResponse(null,1000,"Success");
        }else {
            return CommonResponse.generateResponse(null,1001,"Project already exists");
        }
    }

    //List All Employee
    @GetMapping(path = "/all")
    public @ResponseBody CommonResponse getAllEmployee() {
        return CommonResponse.generateResponse(employeeRepository.findAll(),1000,"Success");

    }

    //Update Employee
    @PostMapping(path = "/update/{id}")
    public CommonResponse updateEmployee(@RequestBody Employee employee, @PathVariable("id") Integer employeeId){
        Employee employeeDB = employeeRepository.findById(employeeId).get();


        if (Objects.nonNull(employee.getEmployeeName())
                && !"".equalsIgnoreCase(
                employee.getEmployeeName())) {
            employeeDB.setEmployeeName(
                    employee.getEmployeeName());
        }

        if (Objects.nonNull(employee.getPosition())
                && !"".equalsIgnoreCase(
                employee.getPosition())) {
            employeeDB.setPosition(
                    employee.getPosition());
        }

        if (Objects.nonNull(employee.getBio())
                && !"".equalsIgnoreCase(
                employee.getBio())) {
            employeeDB.setBio(
                    employee.getBio());
        }

        if (Objects.nonNull(employee.getEmail())
                && !"".equalsIgnoreCase(
                employee.getEmail())) {
            employeeDB.setEmail(
                    employee.getEmail());
        }

        if (Objects.nonNull(employee.getStreamUrl())
                && !"".equalsIgnoreCase(
                employee.getStreamUrl())) {
            employeeDB.setStreamUrl(
                    employee.getStreamUrl());
        }

        return CommonResponse.generateResponse(employeeRepository.save(employeeDB),1000,"Success");

    }

    //Delete Employee

    @GetMapping(path = "/delete/{id}")
    public @ResponseBody CommonResponse deleteEmployeeById(@PathVariable("id") Integer employeeId){
        employeeRepository.deleteById(employeeId);

        return CommonResponse.generateResponse(null,1000,"Deleted Successfully");
    }


}
