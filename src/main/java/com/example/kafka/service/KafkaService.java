package com.example.kafka.service;

import com.example.kafka.dto.KafkaDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class KafkaService {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC_NAME = "topic-name-test-kafka";

    public void sendKafkaJsonSerializer() {
        KafkaDto kafkaDto =
                KafkaDto.builder()
                        .id(UUID.randomUUID().toString())
                        .name("DICKY")
                        .address("JAKARTA")
                        .mobilePhone("08333333333")
                        .build();
        CompletableFuture<SendResult<String, Object>> kafkaResultFuture =
                kafkaTemplate.send(TOPIC_NAME, kafkaDto);
        addCallback(kafkaDto, (ListenableFuture<SendResult<String, Object>>) kafkaResultFuture);
        log.info("kafkaDto --> {}", kafkaDto);
    }


    private void addCallback(Object message,
                             ListenableFuture<SendResult<String, Object>> kafkaResultFuture) {
        kafkaResultFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("Error while sending message {} to topic {}", message.toString(), KafkaService.TOPIC_NAME, throwable);
            }

            @Override
            public void onSuccess(SendResult<String, Object> stringStringSendResult) {
                log.info("Success Send {} ", stringStringSendResult);
            }
        });
    }

    @KafkaListener(id = TOPIC_NAME+"-ID",
            topics = {TOPIC_NAME},
            groupId = TOPIC_NAME+"-GROUP")
    public void listener(@Payload KafkaDto kafkaDto){
        log.info("listener {} ", kafkaDto);
    }

}
