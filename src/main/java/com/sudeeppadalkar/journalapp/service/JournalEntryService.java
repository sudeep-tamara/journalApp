package com.sudeeppadalkar.journalapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sudeeppadalkar.journalapp.entity.JournalEntries;
import com.sudeeppadalkar.journalapp.entity.User;
import com.sudeeppadalkar.journalapp.repository.JournalEntryRepository;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntries journalEntity) {
        try {
            User user = userService.getLoggedInUser();

            JournalEntries saved = journalEntryRepository.save(journalEntity);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occured while saving the entry", e);
        }
    }

    public List<JournalEntries> getAll() {
        return journalEntryRepository.findAll();
    }

    public JournalEntries findEntryById(ObjectId id) {
        return getJournalForLoggedInUser(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id) {
        try {
            boolean journalDeleted = false;
            User user = userService.getLoggedInUser();

            if (getJournalForLoggedInUser(id) != null) {
                user.getJournalEntries().removeIf(x -> x.getId().equals(id));
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
                journalDeleted = true;
            }
            return journalDeleted;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("An error occurred while deleting the entry", e);
        }

    }

    private JournalEntries getJournalForLoggedInUser(ObjectId id) {
        User user = userService.getLoggedInUser();

        List<JournalEntries> collect = user.getJournalEntries()
                .stream()
                .filter(x -> x.getId().equals(id))
                .collect(Collectors.toList());

        if (!collect.isEmpty()) {
            return collect.get(0);
        }
        return null;
    }

}
