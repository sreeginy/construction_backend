package com.system.repository;

import com.system.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders,Integer> {
    default void saveOrder(Orders orders){
        save(orders);
    }
}

