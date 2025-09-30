package com.edigest.journalApp.controller;

import com.edigest.journalApp.entity.JournalEntity;
import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.service.JournalEntryServiceV2;
import com.edigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journalv2")
public class JournalEntryControllerV2 {

    @Autowired
    JournalEntryServiceV2 journalEntryServiceV2;
    @Autowired
    UserService userService;

    @GetMapping("/getAllJournalEntriesOfUser")
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntity> journalEntities = user.getJournalEntities();// journalEntryServiceV2.getAll();
        if (journalEntities != null && !journalEntities.isEmpty()) {
            return new ResponseEntity<>(journalEntities, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntity> getById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntity> collect = user.getJournalEntities().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            collect.get(0).getId();
            Optional<JournalEntity> journalEntity = journalEntryServiceV2.getById(myId);
            return new ResponseEntity<>(journalEntity.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed = journalEntryServiceV2.deleteById(userName, myId);
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateById(@PathVariable ObjectId
                                                myId, @RequestBody JournalEntity newJournalEntity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntity> collect = user.getJournalEntities().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<JournalEntity> journalEntity = journalEntryServiceV2.getById(myId);
            if (journalEntity.isPresent()) {
                JournalEntity old = journalEntity.get();
                old.setContent(newJournalEntity.getContent() != null && !newJournalEntity.getContent().equals("") ? newJournalEntity.getContent() : old.getContent());
                old.setTitle(newJournalEntity.getTitle() != null && !newJournalEntity.getTitle().equals("") ? newJournalEntity.getTitle() : old.getTitle());
                journalEntryServiceV2.updateById(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/createEntry")
    public ResponseEntity<JournalEntity> createEntry(@RequestBody JournalEntity journalEntity) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User user = userService.findByUserName(userName);
            journalEntryServiceV2.saveEntry(journalEntity, user);
            return new ResponseEntity<>(journalEntity, HttpStatus.CREATED);// journalEntity.getTitle() + " Created";
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
