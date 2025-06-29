package com.sudeeppadalkar.journalapp.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document(collection = "config_journal_app")
@Data
@Builder
public class JournalConfig {
    @Id
    private ObjectId id;
    private String key;
    private String value;

}
