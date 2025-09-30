package com.edigest.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String key, String msg) {
        kafkaTemplate.send("fruits", key, msg);
    }

    @KafkaListener(topics = "fruits", groupId = "fruits")
    public void listen(String message) {
        System.out.println("Received: " + message);
    }

//    @KafkaListener(topics = "fruits", groupId = "fruits")
//    public void listen(ConsumerRecord<String, String> record) {
//        String key = record.key();     // could be null
//        String value = record.value(); // always present
//        System.out.println("Received key: " + key + ", value: " + value);
//    }
}
