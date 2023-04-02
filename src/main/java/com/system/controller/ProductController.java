package com.system.controller;

import com.system.model.Category;
import com.system.model.CommonResponse;
import com.system.model.Customers;
import com.system.model.Product;
import com.system.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping(path = "/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    //Add New Product
    @PostMapping(path = "/add")
    public @ResponseBody CommonResponse addNewProduct(@RequestBody Product product){
        Product productDB = productRepository.findByProductName(product.getProductName());

        if (productDB == null){
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate= formatter.format(date);
            product.setCreatedAt(strDate);
            productRepository.save(product);
            return CommonResponse.generateResponse(null,1000,"Success");
        }else {
            return CommonResponse.generateResponse(null,1001,"Product already exists");
        }
    }

    //List All Product
    @GetMapping(path = "/all")
    public @ResponseBody CommonResponse getAllProducts() {
        return CommonResponse.generateResponse(productRepository.findAll(),1000,"Success");

    }

    //Update Product
    @PostMapping(path = "/update/{id}")
    public CommonResponse updateProduct(@RequestBody Product product,@PathVariable("id") Integer productId){
        Product productDB = productRepository.findById(productId).get();

        if (Objects.nonNull(product.getProductName())
                && !"".equalsIgnoreCase(
                product.getProductName())) {
            productDB.setProductName(
                    product.getProductName());
        }

        if (Objects.nonNull(product.getPrice())
                && !"".equalsIgnoreCase(
                product.getPrice().toString())) {
            productDB.setPrice(
                    product.getPrice());
        }

        if (Objects.nonNull(product.getCategory())
                && !"".equalsIgnoreCase(
                product.getCategory().toString())) {
            productDB.setCategory(
                    product.getCategory());
        }

        return CommonResponse.generateResponse(productRepository.save(productDB),1000,"Success");

    }

    //Delete Product
    @GetMapping(path = "/delete/{id}")
    public @ResponseBody CommonResponse deleteProductById(@PathVariable("id") Integer productId){
        productRepository.deleteById(productId);

        return CommonResponse.generateResponse(null,1000,"Deleted Successfully");
    }
}
