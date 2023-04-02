package com.system.controller;

import com.system.model.CommonResponse;
import com.system.model.Orders;
import com.system.model.Product;
import com.system.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    //Add Customer
    @PostMapping(path = "/add")
    public @ResponseBody CommonResponse createOrder(@RequestBody Orders orders){
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate= formatter.format(date);
            orders.setCreatedAt(strDate);
            orderRepository.save(orders);
            return CommonResponse.generateResponse(null,1000,"Success");
    }

    //List All customer
    @GetMapping(path = "/all")
    public @ResponseBody CommonResponse getAllOrders() {
        return CommonResponse.generateResponse(orderRepository.findAll(),1000,"Success");

    }

    //Update customer
    @PutMapping(path = "/update/{id}")
    public CommonResponse updateOrder(@RequestBody Orders orders,@PathVariable("id") Integer orderId){
        Orders ordersDB = orderRepository.findById(orderId).get();


        if (Objects.nonNull(orders.getOrderStatus())
                && !"".equalsIgnoreCase(
                orders.getOrderStatus())) {
            ordersDB.setOrderStatus(
                    orders.getOrderStatus());
        }
        orderRepository.save(ordersDB);
        //orderRepository.saveOrder(ordersDB);
        return CommonResponse.generateResponse(null,1000,"Updated Successfully");
    }

    //Delete customer
    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody CommonResponse deleteOrderById(@PathVariable("id") Integer orderId){
        orderRepository.deleteById(orderId);

        return CommonResponse.generateResponse(null,1000,"Deleted Successfully");
    }

}
