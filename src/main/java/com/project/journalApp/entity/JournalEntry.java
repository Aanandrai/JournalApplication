package com.project.journalApp.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

//It means it is mongodb se mapped entity hai
//collection are optional and means map the entity with collection name in bd
@Document(collection ="journal_entries")
@Data
@Getter
@Setter
@NoArgsConstructor
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
