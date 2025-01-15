package com.consoleservice.service;

import com.consoleservice.model.OrderEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Slf4j
@Service
@AllArgsConstructor
public class KafkaMessagingService {
    private static final String topicCreateOrder = "${topic.send-order}";
    private static final String kafkaConsumerGroupId = "${spring.kafka.consumer.group-id}";

    @KafkaListener(topics = topicCreateOrder, groupId = kafkaConsumerGroupId, properties = {"spring.json.value.default.type=com.consoleservice.model.OrderEvent"})
    public Mono<OrderEvent> printOrder(OrderEvent orderEvent) {
        return Mono.just(orderEvent)
                .doOnNext(event -> {
                    log.info("The product: {} was ordered in quantity: {} and at a price: {}", event.getProductName(), event.getQuantity(), event.getPrice());
                    log.info("To pay: {}", new BigDecimal(event.getQuantity()).multiply(event.getPrice()));
                });
    }
}
