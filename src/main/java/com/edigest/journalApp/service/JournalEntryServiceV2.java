package com.edigest.journalApp.service;

import com.edigest.journalApp.entity.JournalEntity;
import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.repository.JournalEntryRepositoryV2;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryServiceV2 {

    @Autowired
    UserService userService;
    @Autowired
    private JournalEntryRepositoryV2 journalEntryRepositoryV2;

    public List<JournalEntity> getAll() {
        return journalEntryRepositoryV2.findAll();
    }

    @Transactional
    public void saveEntry(JournalEntity journalEntity, User user) {
        try {
            journalEntity.setDate(LocalDateTime.now());
            JournalEntity saved = journalEntryRepositoryV2.save(journalEntity);
//            user.setUserName(null);
            user.getJournalEntities().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An Error occured while saving new Entry.", e);
        }
    }

    public void updateById(JournalEntity journalEntity) {
        journalEntity.setDate(LocalDateTime.now());
        JournalEntity saved = journalEntryRepositoryV2.save(journalEntity);
    }

    public Optional<JournalEntity> getById(ObjectId myId) {
        return journalEntryRepositoryV2.findById(myId);
    }

    @Transactional
    public boolean deleteById(String userName, ObjectId myId) {
        boolean removed = false;
        try {
            User user = userService.findByUserName(userName);
            removed = user.getJournalEntities().removeIf(x -> x.getId().equals(myId));
            if (removed) {
                userService.saveUser(user);
                journalEntryRepositoryV2.deleteById(myId);
            }
        } catch (Exception e) {
            log.error("Error occurred", e);
//            System.out.println(e);
            throw new RuntimeException("An Error occurred while deleting the entry.", e);
        }
        return removed;
    }

    public User findByUserName(String userName) {
        return userService.findByUserName(userName);
    }
}