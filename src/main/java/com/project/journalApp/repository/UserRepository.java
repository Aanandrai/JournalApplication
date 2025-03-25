package com.project.journalApp.repository;

import com.project.journalApp.entity.JournalEntry;
import com.project.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
//mongoRepository is an interface which have many method for database interaction


public interface UserRepository extends MongoRepository<User, ObjectId> {
//   MongoRepository <Entity,id_type>

    User findByUserName(String username);

    void deleteByUserName(String userName);
}
