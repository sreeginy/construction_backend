package com.system.controller;


import com.system.config.JwtTokenUtil;
import com.system.model.*;
import com.system.repository.UserRepository;
import com.system.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    Logger logger = Logger.getLogger(this.getClass().getName());
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResponse createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        LoginResponse response = new LoginResponse();
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        User user = userRepository.findByEmail(authenticationRequest.getUsername());
        if (user != null){
            response.setId(user.getId());
            response.setEmail(user.getEmail());
            response.setFirstName(user.getFirstName());
            response.setLastName(user.getLastName());
            response.setToken(token);

            CommonResponse objreturn = CommonResponse.generateResponse(response,1000,"Login Success");
            return objreturn;
        }else {
            return CommonResponse.generateResponse(response,1001,"Invalid Email / Password");
        }
    }

    //Add New User
    @PostMapping(path = "/add")
    public @ResponseBody CommonResponse addNewUser(@RequestBody User user){
        User userDB = userRepository.findByEmail(user.getEmail());
        if (userDB == null){
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate= formatter.format(date);
            user.setPassword(bcryptEncoder.encode(user.getPassword()));
            user.setCreatedAt(strDate);
            userRepository.save(user);
            return CommonResponse.generateResponse(null,1000,"Success");
        }else {
            return CommonResponse.generateResponse(null,1001,"User already exists");
        }





    }

    //List All users

    @GetMapping(path = "/all")
    public @ResponseBody CommonResponse getAllUsers() {
        logger.info("Called");
        return CommonResponse.generateResponse(userRepository.findAll(),1000,"Success");
    }

    //Update User
    @PostMapping(path = "/update/{id}")
    public CommonResponse updateUser(@RequestBody User user,@PathVariable("id") Integer userId){
        User userDB = userRepository.findById(userId).get();
        User userDB1 = userRepository.findByEmail(user.getEmail());
        boolean isEmailChanged = false;
        if (Objects.nonNull(user.getFirstName())
                && !"".equalsIgnoreCase(
                user.getFirstName()) && !userDB.getFirstName().equals(user.getFirstName())) {
            userDB.setFirstName(
                    user.getFirstName());
        }

        if (Objects.nonNull(user.getLastName())
                && !"".equalsIgnoreCase(
                user.getLastName()) && !userDB.getLastName().equals(user.getLastName())) {
            userDB.setLastName(
                    user.getLastName());
        }


        if (Objects.nonNull(user.getEmail())
                && !"".equalsIgnoreCase(
                user.getEmail()) && !userDB.getEmail().equals(user.getEmail())) {
            isEmailChanged = true;
            userDB.setEmail(
                    user.getEmail());
        }
        if (isEmailChanged ){
            if (userDB1 == null){
                return CommonResponse.generateResponse(userRepository.save(userDB),1000,"Success");
            }else {
                return CommonResponse.generateResponse(null ,1001,"Email already exist");
            }

        }else {
            return CommonResponse.generateResponse(userRepository.save(userDB),1000,"Success");
        }

    }

    //Delete User
    @GetMapping(path = "/delete/{id}")
    public @ResponseBody CommonResponse deleteUserById(@PathVariable("id") Integer userId){
        userRepository.deleteById(userId);

        return CommonResponse.generateResponse(null,1000,"Deleted Successfully");
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
