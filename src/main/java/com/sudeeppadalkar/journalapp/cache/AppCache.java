package com.sudeeppadalkar.journalapp.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sudeeppadalkar.journalapp.entity.JournalConfig;
import com.sudeeppadalkar.journalapp.repository.ConfigJournalAppRepository;

import jakarta.annotation.PostConstruct;

@Component
public class AppCache {

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String, String> APP_CACHE = new HashMap<>();

    @PostConstruct
    public void init() {
        List<JournalConfig> allConfig = configJournalAppRepository.findAll();
        for (JournalConfig journalConfig : allConfig) {
            APP_CACHE.put(journalConfig.getKey(), journalConfig.getValue());
        }
    }

}
