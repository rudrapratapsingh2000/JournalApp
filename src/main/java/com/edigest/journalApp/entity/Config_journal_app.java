package com.edigest.journalApp.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "config_journal_app")
@Data
@Getter
@Setter
public class Config_journal_app {

    private String key;
    private String value;

}
