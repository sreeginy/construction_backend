package com.system.repository;

import com.system.model.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customers,Integer> {
    Customers findByEmail(String email);
}
