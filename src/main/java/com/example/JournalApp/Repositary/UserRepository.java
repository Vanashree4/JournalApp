package com.example.JournalApp.Repositary;

import com.example.JournalApp.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface   UserRepository extends MongoRepository<User, ObjectId> {


    User findByUserName(String username);



    void deleteByUserName(String username);
}
