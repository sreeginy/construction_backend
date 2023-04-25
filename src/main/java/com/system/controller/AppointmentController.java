package com.system.controller;

import com.system.model.Appointment;
import com.system.model.CommonResponse;
import com.system.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping(path = "/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    //Add New Appointment
    @PostMapping(path = "/add")
    public @ResponseBody CommonResponse addNewAppointment(@RequestBody Appointment appointment){
        Appointment appointmentDB = appointmentRepository.findByFirstName(appointment.getFirstName());

        if (appointmentDB == null){
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate= formatter.format(date);
            appointment.setCreatedAt(strDate);
            appointmentRepository.save(appointment);
            return CommonResponse.generateResponse(null,1000,"Success");
        }else {
            return CommonResponse.generateResponse(null,1001,"Product already exists");
        }
    }

    //List All Appointment
    @GetMapping(path = "/all")
    public @ResponseBody CommonResponse getAllAppointments() {
        return CommonResponse.generateResponse(appointmentRepository.findAll(),1000,"Success");

    }

    //Update Appointment
    @PostMapping(path = "/update/{id}")
    public CommonResponse updateAppointment(@RequestBody Appointment appointment, @PathVariable("id") Integer appointmentId){
        Appointment appointmentDB = appointmentRepository.findById(appointmentId).get();

        if (Objects.nonNull(appointment.getFirstName())
                && !"".equalsIgnoreCase(
                appointment.getFirstName())) {
            appointmentDB.setFirstName(
                    appointment.getFirstName());
        }

        if (Objects.nonNull(appointment.getLastName())
                && !"".equalsIgnoreCase(
                appointment.getLastName())) {
            appointmentDB.setLastName(
                    appointment.getLastName());
        }


        if (Objects.nonNull(appointment.getEmail())
                && !"".equalsIgnoreCase(
                appointment.getEmail())) {
            appointmentDB.setEmail(
                    appointment.getEmail());
        }

        if (Objects.nonNull(appointment.getJoinDate())
                && !"".equalsIgnoreCase(
                appointment.getJoinDate())) {
            appointmentDB.setJoinDate(
                    appointment.getJoinDate());
        }

        if (Objects.nonNull(appointment.getPackages())
                && !"".equalsIgnoreCase(
                appointment.getPackages())) {
            appointmentDB.setPackages(
                    appointment.getPackages());
        }

        if (Objects.nonNull(appointment.getStatus())
                && !"".equalsIgnoreCase(
                appointment.getStatus())) {
            appointmentDB.setStatus(
                    appointment.getStatus());
        }
        return CommonResponse.generateResponse(appointmentRepository.save(appointmentDB),1000,"Success");
    }

    //Delete appointment
    @GetMapping(path = "/delete/{id}")
    public @ResponseBody CommonResponse deleteAppointmentById(@PathVariable("id") Integer appointmentId){
        appointmentRepository.deleteById(appointmentId);

        return CommonResponse.generateResponse(null,1000,"Deleted Successfully");
    }
    @GetMapping(path = "/count")
    public @ResponseBody long getCount(){
        return appointmentRepository.count();
    }

}
