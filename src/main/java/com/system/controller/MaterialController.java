package com.system.controller;

import com.system.model.CommonResponse;
import com.system.model.Material;
import com.system.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping(path = "/material")
public class MaterialController {

    @Autowired
    private MaterialRepository materialRepository;

    //Add New Material
    @PostMapping(path = "/add")
    public @ResponseBody CommonResponse addNewMaterial(@RequestBody Material material){
        Material materialDB = materialRepository.findByName(material.getName());

        if (materialDB == null){
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate= formatter.format(date);
            material.setCreatedAt(strDate);
            materialRepository.save(material);
            return CommonResponse.generateResponse(null,1000,"Success");
        }else {
            return CommonResponse.generateResponse(null,1001,"Material already exists");
        }
    }

    //List All Material
    @GetMapping(path = "/all")
    public @ResponseBody CommonResponse getAllMaterial() {
        return CommonResponse.generateResponse(materialRepository.findAll(),1000,"Success");
    }

    //Update Material
    @PostMapping(path = "/update/{id}")
    public CommonResponse updateMaterial(@RequestBody Material Material,@PathVariable("id") Integer materialId){
        Material materialDB = materialRepository.findById(materialId).get();

        if (Objects.nonNull(Material.getName())
                && !"".equalsIgnoreCase(
                Material.getName())) {
            materialDB.setName(
                    Material.getName());
        }

        if (Objects.nonNull(Material.getPackages())
                && !"".equalsIgnoreCase(
                Material.getPackages())) {
            materialDB.setPackages(
                    Material.getPackages());
        }

        if (Objects.nonNull(Material.getCost())
                && !"".equalsIgnoreCase(
                Material.getCost().toString())) {
            materialDB.setCost(
                    Material.getCost());
        }
        return CommonResponse.generateResponse(materialRepository.save(materialDB),1000,"Success");

    }

    //Delete Material
    @GetMapping(path = "/delete/{id}")
    public @ResponseBody CommonResponse deleteMaterialById(@PathVariable("id") Integer materialId){
        materialRepository.deleteById(materialId);

        return CommonResponse.generateResponse(null,1000,"Deleted Successfully");
    }
}
