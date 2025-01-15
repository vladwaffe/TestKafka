package com.consumerservice.service;

import com.consumerservice.model.OrderEvent;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class KafkaMessagingService {
    private static final String topicCreateOrder = "${topic.send-order}";
    private static final String kafkaConsumerGroupId = "${spring.kafka.consumer.group-id}";
    private final ModelMapper modelMapper;

    @Transactional
    @KafkaListener(topics = topicCreateOrder, groupId = kafkaConsumerGroupId, properties = {"spring.json.value.default.type=com.consumerservice.model.OrderEvent"})
    public OrderEvent createOrder(OrderEvent orderEvent) {
        log.info("Message consumed {}", orderEvent);
        //orderService.save(modelMapper.map(orderEvent, OrderEvent.class));
        return orderEvent;
    }

}