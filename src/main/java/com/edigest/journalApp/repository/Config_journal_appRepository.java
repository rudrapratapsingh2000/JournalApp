package com.edigest.journalApp.repository;

import com.edigest.journalApp.entity.Config_journal_app;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Config_journal_appRepository extends MongoRepository<Config_journal_app, String> {
}
