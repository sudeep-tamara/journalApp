package com.sudeeppadalkar.journalapp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.sudeeppadalkar.journalapp.entity.JournalConfig;

public interface ConfigJournalAppRepository extends MongoRepository<JournalConfig, ObjectId> {

}
