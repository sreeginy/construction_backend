package com.system.repository;

import com.system.model.Category;
import com.system.model.Partners;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.servlet.http.Part;

public interface PartnerRepository extends JpaRepository<Partners,Integer> {
    Partners findByName(String name);
}
