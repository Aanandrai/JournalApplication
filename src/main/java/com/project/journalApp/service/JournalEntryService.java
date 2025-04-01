package com.project.journalApp.service;

import com.project.journalApp.entity.JournalEntry;
import com.project.journalApp.entity.User;
import com.project.journalApp.exception.ErrorResponse;
import com.project.journalApp.exception.UserNotFoundException;
import com.project.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

//    It is dependency injection
//    Spring runtime pe iska implementation aap ke liye generate ker dega vo isme inject ho jaye ga
    @Transactional
    public void saveEntry(JournalEntry journalEntry,String userName){
        try {
            User user=userService.findByUserName(userName);

            if (user == null) {
                // You can log an error or throw an exception if desired
                throw new UserNotFoundException("User not found with username: " + userName);
            }

            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved=journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        }catch(Exception e){
            throw new RuntimeException("Error occurred while saving journal entry", e);
        }

    }

//
    public void saveEntry(JournalEntry journalEntry){
        try{
            journalEntryRepository.save(journalEntry);
        }catch(Exception e){
            throw new RuntimeException("Error occurred while saving journal entry", e);
        }

    }



    public List<JournalEntry> getAll(){
       return journalEntryRepository.findAll();
    }


    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public void deleteById(ObjectId id,String userName){
        User user =userService.findByUserName(userName);
        if (user == null) {
            throw new UserNotFoundException("User not found with username: " + userName);

        }
        boolean removed=user.getJournalEntries().removeIf(x->x.getId().equals(id));
        if(!removed){
            throw new UserNotFoundException("Journal Not Found");
        }
        userService.saveUser(user);
        journalEntryRepository.deleteById(id);

    }







}
