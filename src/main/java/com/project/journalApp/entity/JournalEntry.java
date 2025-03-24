package com.project.journalApp.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

//It means it is mongodb se mapped entity hai
//collection are optional and means map the entity with collection name in bd
@Document(collection ="journal_entries")
@Getter
@Setter
// To use getter and setter use install a plugin name Lombok in intaliJ
//also use the dependency name Lombok
public class JournalEntry {

    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;



}
