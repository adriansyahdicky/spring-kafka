package com.example.kafka.contorller;

import com.example.kafka.service.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class KafkaController {

    @Autowired
    private KafkaService kafkaService;

    @GetMapping("/send/kafka")
    public void send(){
        log.info("Send kafka message ...");
        kafkaService.sendKafkaJsonSerializer();
    }

}
