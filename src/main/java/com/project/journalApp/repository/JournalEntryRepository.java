package com.project.journalApp.repository;

import com.project.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
//mongoRepository is an interface which have many method for database interaction


public interface JournalEntryRepository  extends MongoRepository<JournalEntry, ObjectId> {
//   MongoRepository <Entity,id_type>
}
