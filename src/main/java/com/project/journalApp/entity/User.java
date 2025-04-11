package com.project.journalApp.entity;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Document(collection ="user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private ObjectId id;
    @Indexed(unique=true)   //despite setting Indexed it does not create indexing we have set it in application.properties
    @NonNull
    private String userName;


    @NonNull
    private String password;

    private String email;


    @DBRef
    private List<JournalEntry> journalEntries=new ArrayList<>();

    private List<String> roles;


}
