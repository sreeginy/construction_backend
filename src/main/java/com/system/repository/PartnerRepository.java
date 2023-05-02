package com.system.repository;

import com.system.model.Partners;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerRepository extends JpaRepository<Partners,Integer> {
    Partners findByName(String name);

}
