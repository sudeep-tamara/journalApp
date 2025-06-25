package com.sudeeppadalkar.journalapp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.sudeeppadalkar.journalapp.entity.JournalEntries;

public interface JournalEntryRepository extends MongoRepository<JournalEntries, ObjectId> {

}
