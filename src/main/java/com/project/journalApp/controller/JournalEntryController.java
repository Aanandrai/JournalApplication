package com.project.journalApp.controller;


import com.project.journalApp.entity.JournalEntry;
import com.project.journalApp.entity.User;
import com.project.journalApp.exception.ErrorResponse;
import com.project.journalApp.exception.UserNotFoundException;
import com.project.journalApp.service.JournalEntryService;
import com.project.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;


    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {
        try {
            User user=userService.findByUserName(userName);
            if(user==null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            List<JournalEntry>all=user.getJournalEntries();
            return new ResponseEntity<>(all,HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping("/{userName}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry , @PathVariable String userName) {
        try {

            journalEntryService.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            // Handle the case where the user was not found
            ErrorResponse errorResponse = new ErrorResponse("User not found with username: " + userName, HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(errorResponse , HttpStatus.NOT_FOUND);  // Return a 404 status if the user is not found

        } catch(Exception e){
            ErrorResponse errorResponse = new ErrorResponse("User not found with username: " + userName, HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
        }

    }




    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
        Optional<JournalEntry>journalEntry=journalEntryService.findById(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("id/{userName}/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId , @PathVariable String userName) {
        try {
            journalEntryService.deleteById(myId,userName);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(UserNotFoundException e){
            ErrorResponse error=new ErrorResponse(e.getMessage(),HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
        }catch(Exception e){
            ErrorResponse error=new ErrorResponse(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/id/userName/{id}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable ObjectId id,@PathVariable String userName, @RequestBody JournalEntry newEntry) {
//
        JournalEntry old=journalEntryService.findById(id).orElse(null);
        if(old!=null){
            old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("")?newEntry.getTitle():old.getTitle());
            old.setContent(newEntry.getContent()!=null && !newEntry.equals("")?newEntry.getContent():old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}


//controller -----> service  ----> repository
// controller calls service  and service calls repository
