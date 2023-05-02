package com.system.repository;

import com.system.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {
    Appointment findByFirstName(String name);
    
}
