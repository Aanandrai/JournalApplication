package com.project.journalApp.controller;


import com.project.journalApp.cache.AppCache;
import com.project.journalApp.entity.User;
import com.project.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;





    @GetMapping("/all-users")
    public ResponseEntity<?>getAllUsers(){
        List<User> all=userService.getAll();

        if (all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/create-admin-user")
    public ResponseEntity<?> addNewAdmin(@RequestBody User user){
        userService.saveAdmin(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/clear-app-cache")
    public void clearAppCache(){
        appCache.init();
    }




}
