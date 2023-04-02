package com.system.controller;

import com.system.model.CommonResponse;
import com.system.model.Customers;
import com.system.model.User;
import com.system.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping(path = "/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    //Add Customer
    @PostMapping(path = "/add")
    public @ResponseBody CommonResponse addNewCustomer(@RequestBody Customers customers){
        Customers customerDB = customerRepository.findByEmail(customers.getEmail());
        if (customerDB == null){
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate= formatter.format(date);
            customers.setPassword(bcryptEncoder.encode(customers.getPassword()));
            customers.setCreatedAt(strDate);
            customerRepository.save(customers);
            return CommonResponse.generateResponse(null,1000,"Success");
        }else {
            return CommonResponse.generateResponse(null,1001,"Customer already exists");
        }
    }

    //List All customer
    @GetMapping(path = "/all")
    public @ResponseBody CommonResponse getAllCustomer() {
        return CommonResponse.generateResponse(customerRepository.findAll(),1000,"Success");

    }

    //Update customer
    @PostMapping(path = "/update/{id}")
    public CommonResponse updateCustomer(@RequestBody Customers customers,@PathVariable("id") Integer customerId){
        Customers customersDB = customerRepository.findById(customerId).get();
        Customers customersDB1 = customerRepository.findByEmail(customers.getEmail());
        boolean isEmailChanged = false;
        if (Objects.nonNull(customers.getFirstName())
                && !"".equalsIgnoreCase(
                customers.getFirstName())) {
            customersDB.setFirstName(
                    customers.getFirstName());
        }

        if (Objects.nonNull(customers.getLastName())
                && !"".equalsIgnoreCase(
                customers.getLastName())) {
            customersDB.setLastName(
                    customers.getLastName());
        }

        if (Objects.nonNull(customers.getContactNo())
                && !"".equalsIgnoreCase(
                String.valueOf(customers.getContactNo()))) {
            customersDB.setContactNo(
                    customers.getContactNo());
        }

        if (Objects.nonNull(customers.getAddress())
                && !"".equalsIgnoreCase(
                customers.getAddress())) {
            customersDB.setAddress(
                    customers.getAddress());
        }

        if (Objects.nonNull(customers.getDeliveryAddress())
                && !"".equalsIgnoreCase(
                customers.getDeliveryAddress())) {
            customersDB.setDeliveryAddress(
                    customers.getDeliveryAddress());
        }

        if (Objects.nonNull(customers.getPassword())
                && !"".equalsIgnoreCase(
                customers.getPassword())) {
            customersDB.setPassword(bcryptEncoder.encode(customers.getPassword()));

        }
        if (Objects.nonNull(customers.getEmail())
                && !"".equalsIgnoreCase(
                customers.getEmail()) && !customersDB.getEmail().equals(customers.getEmail())) {
            isEmailChanged = true;
            customersDB.setEmail(
                    customers.getEmail());
        }

        if (isEmailChanged ){
            if (customersDB1 == null){
                return CommonResponse.generateResponse(customerRepository.save(customersDB),1000,"Success");
            }else {
                return CommonResponse.generateResponse(null ,1001,"Email already exist");
            }

        }else {
            return CommonResponse.generateResponse(customerRepository.save(customersDB),1000,"Success");
        }

    }

    //Delete customer
    @GetMapping(path = "/delete/{id}")
    public @ResponseBody CommonResponse deleteCustomerById(@PathVariable("id") Integer customerId){
        customerRepository.deleteById(customerId);

        return CommonResponse.generateResponse(null,1000,"Deleted Successfully");

    }
}
