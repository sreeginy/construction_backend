package com.system.controller;

import com.system.model.Category;
import com.system.model.CommonResponse;
import com.system.model.User;
import com.system.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping(path = "/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping(path = "/add")
    public @ResponseBody CommonResponse addNewCategory(@RequestBody Category category){
        Category categoryDB = categoryRepository.findByName(category.getName());
        if (categoryDB == null){
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate= formatter.format(date);
            category.setCreatedAt(strDate);
            categoryRepository.save(category);
            return CommonResponse.generateResponse(null,1000,"Success");

        }else {
            return CommonResponse.generateResponse(null,1001,"Category already exists");

        }
    }

    //List All users
    @GetMapping(path = "/all")
    public @ResponseBody CommonResponse getAllCategories() {
        return CommonResponse.generateResponse(categoryRepository.findAll(),1000,"Success");

    }

    //Update User
    @PostMapping(path = "/update/{id}")
    public CommonResponse updateCategory(@RequestBody Category category,@PathVariable("id") Integer categoryId){
        Category catDB = categoryRepository.findById(categoryId).get();
        Category catDB1 = categoryRepository.findByName(category.getName());
        if (Objects.nonNull(category.getCode())
                && !"".equalsIgnoreCase(
                category.getCode())&& !catDB.getCode().equals(category.getCode())) {
            catDB.setCode(
                    category.getCode());
        }

        if (Objects.nonNull(category.getName())
                && !"".equalsIgnoreCase(
                category.getName())&& !catDB.getName().equals(category.getName())) {
            catDB.setName(
                    category.getName());
        }
        if (catDB1 != null){
            if (Objects.equals(catDB1.getId(), categoryId)){
                return CommonResponse.generateResponse(categoryRepository.save(catDB),1000,"Success");
            }else {
                return CommonResponse.generateResponse(null ,1001,"category already exist");

            }
        }else {
            return CommonResponse.generateResponse(categoryRepository.save(catDB),1000,"Success");

        }
    }

    //Delete User
    @GetMapping(path = "/delete/{id}")
    public @ResponseBody CommonResponse deleteCategoryById(@PathVariable("id") Integer categoryId){
        categoryRepository.deleteById(categoryId);

        return CommonResponse.generateResponse(null,1000,"Deleted Successfully");
    }
}
