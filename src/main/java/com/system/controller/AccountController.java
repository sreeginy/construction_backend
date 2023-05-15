package com.system.controller;

import com.system.model.Account;
import com.system.model.CommonResponse;
import com.system.model.Employee;
import com.system.model.Product;
import com.system.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping(path = "/account")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    //Add New Account
    @PostMapping(path = "/add")
    public @ResponseBody CommonResponse addNewAccount(@RequestBody Account account){
        Account accountDB = accountRepository.findByWorkerName(account.getWorkerName());

        if (accountDB == null){
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate= formatter.format(date);
            account.setCreatedAt(strDate);
            accountRepository.save(account);
            return CommonResponse.generateResponse(null,1000,"Success!!");
        }else {
            return CommonResponse.generateResponse(null,1001,"Account already exists");
        }
    }
    //List All Account
    @GetMapping(path = "/all")
    public @ResponseBody CommonResponse getAllAccounts() {
        return CommonResponse.generateResponse(accountRepository.findAll(),1000,"Success");
    }

    //Update Account
    @PostMapping(path = "/update/{id}")
    public CommonResponse updateAccount(@RequestBody Account account, @PathVariable("id") Integer accountId){
        Account accountDB = accountRepository.findById(accountId).get();


        if (Objects.nonNull(account.getWorkerName())
                && !"".equalsIgnoreCase(
                account.getWorkerName())) {
            accountDB.setWorkerName(
                    account.getWorkerName());
        }

        if (Objects.nonNull(account.getGender())
                && !"".equalsIgnoreCase(
                account.getGender())) {
            accountDB.setGender(
                    account.getGender());
        }

        if (Objects.nonNull(account.getContactNo())
                && !"".equalsIgnoreCase(
                String.valueOf(account.getContactNo()))) {
            accountDB.setContactNo(
                    account.getContactNo());
        }

        if (Objects.nonNull(account.getWorkDetail())
                && !"".equalsIgnoreCase(
                account.getWorkDetail())) {
            accountDB.setWorkDetail(
                    account.getWorkDetail());
        }

        if (Objects.nonNull(account.getJoinDate())
                && !"".equalsIgnoreCase(
                account.getJoinDate())) {
            accountDB.setJoinDate(
                    account.getJoinDate());
        }

        if (Objects.nonNull(account.getPeriod())
                && !"".equalsIgnoreCase(
                account.getPeriod())) {
            accountDB.setPeriod(
                    account.getPeriod());
        }

        if (Objects.nonNull(account.getPayment())
                && !"".equalsIgnoreCase(
                account.getPayment().toString())) {
            accountDB.setPayment(
                    account.getPayment());
        }

        return CommonResponse.generateResponse(accountRepository.save(accountDB),1000,"Success");
    }


    //Delete Account
    @GetMapping(path = "/delete/{id}")
    public @ResponseBody CommonResponse deleteAccountById(@PathVariable("id") Integer accountId){
        accountRepository.deleteById(accountId);

        return CommonResponse.generateResponse(null,1000,"Deleted Successfully");

    }

}
