package com.edigest.journalApp.cache;

import com.edigest.journalApp.entity.Config_journal_app;
import com.edigest.journalApp.repository.Config_journal_appRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
    public Map<String, String> APP_CACHE = new HashMap<>();
    @Autowired
    Config_journal_appRepository configJournalAppRepository;

    @PostConstruct
    public void init() {
        APP_CACHE = new HashMap<>();
        List<Config_journal_app> config_journal_apps = configJournalAppRepository.findAll();
        for (Config_journal_app configJournalApp : config_journal_apps) {
            APP_CACHE.put(configJournalApp.getKey(), configJournalApp.getValue());
        }
    }
}
