package com.example.kafka.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class KafkaDto {
    private String id;
    private String name;
    private String address;
    private String mobilePhone;
}
