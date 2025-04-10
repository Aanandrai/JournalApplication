package com.project.journalApp.controller;


import com.project.journalApp.api.response.WeatherResponse;
import com.project.journalApp.entity.JournalEntry;
import com.project.journalApp.entity.User;
import com.project.journalApp.service.JournalEntryService;
import com.project.journalApp.service.UserService;
import com.project.journalApp.service.WeatherService;
import org.apache.tomcat.util.http.parser.Authorization;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

//    @GetMapping
//    public ResponseEntity<?> getAllUser(){
//
//        try{
//            return new ResponseEntity<>(userService.getAll() ,HttpStatus.OK);
//
//        }catch(Exception e){
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }



//    @GetMapping("/id/{myId}")
//    public ResponseEntity<?> getUserEntryById(@PathVariable ObjectId myId){
//        try{
//            Optional<User> user=userService.findById(myId);
//            if(user.isPresent()){
//                return new ResponseEntity<>(user ,HttpStatus.OK);
//            }
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }catch(Exception e){
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }

    @DeleteMapping
    public ResponseEntity<?> deleteById(@PathVariable ObjectId myId){
        try{
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            userService.deleteByUserName(authentication.getName());
            Optional<User> user=userService.findById(myId);
            if(user.isPresent()){
                userService.deleteByUserName(authentication.getName());
                return new ResponseEntity<>(user ,HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();

        User userInDb = userService.findByUserName(userName);

            if(userInDb !=null){
                userInDb.setUserName(user.getUserName());
                userInDb.setPassword(user.getPassword());
                userService.saveUser(userInDb);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping
    public ResponseEntity<?> greeting(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse=weatherService.getWeather("Mumbai");
        String greeting="";
        if(weatherResponse!=null){
            greeting=", Weather feels like "+weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi "+ authentication.getName() + greeting, HttpStatus.OK);
    }



}


//controller -----> service  ----> repository
// controller calls service  and service calls repository
