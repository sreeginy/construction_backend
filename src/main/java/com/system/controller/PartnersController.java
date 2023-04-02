package com.system.controller;

import com.system.model.Category;
import com.system.model.CommonResponse;
import com.system.model.Partners;
import com.system.model.User;
import com.system.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping(path = "/partners")
public class PartnersController {

    @Autowired
    private PartnerRepository partnerRepository;

    //Add New Partners
    @PostMapping(path = "/add")
    public @ResponseBody CommonResponse addNewPartners(@RequestBody Partners partners){
        Partners partnerDB = partnerRepository.findByName(partners.getName());
        if (partnerDB == null){
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate= formatter.format(date);
            partners.setCreatedAt(strDate);
            partnerRepository.save(partners);
            return CommonResponse.generateResponse(null,1000,"Success");
        }else {
            return CommonResponse.generateResponse(null,1001,"Partner already exists");
        }
    }

    //List All Partners
    @GetMapping(path = "/all")
    public @ResponseBody CommonResponse getAllPartners() {
        return CommonResponse.generateResponse(partnerRepository.findAll(),1000,"Success");

    }

    //Update Partners
    @PostMapping(path = "/update/{id}")
    public CommonResponse updatePartners(@RequestBody Partners partners,@PathVariable("id") Integer partnerId){
        Partners partnersDB = partnerRepository.findById(partnerId).get();
        if (Objects.nonNull(partners.getName())
                && !"".equalsIgnoreCase(
                partners.getName())) {
            partnersDB.setName(
                    partners.getName());
        }

        if (Objects.nonNull(partners.getCost())
                && !"".equalsIgnoreCase(
                partners.getCost().toString())) {
            partnersDB.setCost(
                    partners.getCost());
        }

        if (Objects.nonNull(partners.getDuration())
                && !"".equalsIgnoreCase(
                partners.getDuration().toString())) {
            partnersDB.setDuration(
                    partners.getDuration());
        }

        return CommonResponse.generateResponse(partnerRepository.save(partnersDB),1000,"Success");

    }

    //Delete Partners
    @GetMapping(path = "/delete/{id}")
    public @ResponseBody CommonResponse deletePartnersById(@PathVariable("id") Integer partnerId){
        partnerRepository.deleteById(partnerId);

        return CommonResponse.generateResponse(null,1000,"Deleted Successfully");

    }
}
