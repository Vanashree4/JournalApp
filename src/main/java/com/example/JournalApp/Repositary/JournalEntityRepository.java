package com.example.JournalApp.Repositary;

import com.example.JournalApp.Entity.JournalEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntityRepository extends MongoRepository<JournalEntity, ObjectId> {
}
