package com.sudeeppadalkar.journalapp.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sudeeppadalkar.journalapp.entity.JournalEntries;
import com.sudeeppadalkar.journalapp.entity.User;
import com.sudeeppadalkar.journalapp.service.JournalEntryService;
import com.sudeeppadalkar.journalapp.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<JournalEntries>> getAllJournalEntries() {
        User user = userService.getLoggedInUser();
        if (user != null) {
            List<JournalEntries> all = user.getJournalEntries();
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @PostMapping
    public ResponseEntity<JournalEntries> createJournalEntry(@RequestBody JournalEntries entry) {
        try {
            journalEntryService.saveEntry(entry);
            return new ResponseEntity<JournalEntries>(entry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<JournalEntries> getJournalEntry(@PathVariable ObjectId id) {
        JournalEntries journalEntry = journalEntryService.findEntryById(id);
        if (journalEntry != null) {
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable ObjectId id, @RequestBody JournalEntries newEntry) {

        // JournalEntries oldJournalEntry =
        // journalEntryService.findEntryById(id).orElse(null);
        // if(oldJournalEntry != null) {
        // oldJournalEntry.setTitle(newEntry.getTitle() != null &&
        // !newEntry.getTitle().equals("") ? newEntry.getTitle() :
        // oldJournalEntry.getTitle());
        // oldJournalEntry.setContent(newEntry.getContent() != null &&
        // !newEntry.getContent().equals("") ? newEntry.getContent() :
        // oldJournalEntry.getContent());
        // journalEntryService.saveEntry(oldJournalEntry);
        // return new ResponseEntity<>(oldJournalEntry, HttpStatus.NO_CONTENT);
        // }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable ObjectId id) {
        return (journalEntryService.deleteById(id))
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

    }
}