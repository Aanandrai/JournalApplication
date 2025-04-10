package com.project.journalApp.repository;

import com.project.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


import java.util.List;

public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;





    public List<User> getUserForSA(){

        Query query=new Query();
//        query.addCriteria(Criteria.where("userName").is("nik"));

//        query.addCriteria(Criteria.where("email").exists(true));
//        query.addCriteria(Criteria.where("email").ne(null).ne(""));

//        in Place of this we can also check it for regular email expression

        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$"));

        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));

//        both query will be run mean they are added by and
//        If you want to add with OR then
//        Criteria criteria=new Criteria()
//        query.addCriteria(criteria.orOperator(Criteria.where("email").exists(true),Criteria.where("sentimentAnalysis").is(true) ));


        List<User>user= mongoTemplate.find(query,User.class);
        return user;
    }

}
