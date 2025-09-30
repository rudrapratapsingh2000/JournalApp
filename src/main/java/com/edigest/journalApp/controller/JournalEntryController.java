package com.edigest.journalApp.controller;

import com.edigest.journalApp.entity.JournalEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    private final Map<String, JournalEntity> journalEntries = new HashMap<>();

    @GetMapping("/getAll")
    public List<JournalEntity> getAll() {
        return new ArrayList<>(journalEntries.values());
    }

    @GetMapping("/id/{myId}")
    public Map getById(@PathVariable String
                               myId) {
        if (journalEntries.get(myId) == null) {
            Map<String, Object> result = new HashMap<>();
            result.put("entity", null);
            return result;
        } else {
            Map<String, Object> result = new HashMap<>();
            result.put("entity", journalEntries.get(myId));
            return result;
        }
    }

    @DeleteMapping("/id/{myId}")
    public String deleteById(@PathVariable String
                                     myId) {
        if (journalEntries.get(myId) != null) {
            String result = journalEntries.get(myId).getTitle() + " Deleted";
            journalEntries.remove(myId);
            return result;
        } else {
            return "No Data Found";
        }
    }

    @PutMapping("/id/{myId}")
    public String updateById(@PathVariable String myId, @RequestBody JournalEntity journalEntity) {
        if (journalEntries.get(myId) != null) {
            String result = journalEntries.get(myId).getTitle() + " Updated";
            journalEntries.put(myId, journalEntity);
            return result;
        } else {
            return "No Data Found";
        }
    }

    @PostMapping("/createEntry")
    public String createEntry(@RequestBody JournalEntity journalEntity) {
        journalEntries.put(journalEntity.getId().toString(), journalEntity);
        return journalEntity.getTitle() + " Created";
    }
}
