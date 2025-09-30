package com.edigest.journalApp.repository;


import com.edigest.journalApp.entity.JournalEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalEntryRepositoryV2 extends MongoRepository<JournalEntity, ObjectId> {

}
