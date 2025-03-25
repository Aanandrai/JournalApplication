package com.project.journalApp.service;

import com.project.journalApp.entity.JournalEntry;
import com.project.journalApp.entity.User;
import com.project.journalApp.repository.JournalEntryRepository;
import com.project.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

//    It is dependency injection
//    Spring runtime pe iska implementation aap ke liye generate ker dega vo isme inject ho jaye ga

    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public void saveEntry(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(Arrays.asList("USER"));
            userRepository.save(user);
        } catch (Exception e) {
//            log.error("Exception ",e);
        }

    }



    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);

    }


    public void deleteByUserName(String userName) {
        userRepository.deleteByUserName(userName);

    }



    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);

    }



}
