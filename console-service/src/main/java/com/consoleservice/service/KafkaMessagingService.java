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

    @KafkaListener(topics = "${topic.send-order}", groupId = "${spring.kafka.consumer.group-id}", properties = {"spring.json.value.default.type=com.consoleservice.model.OrderEvent"})
    public Mono<OrderEvent> printOrder(OrderEvent orderEvent) {
        System.out.println("The product: " + orderEvent.getProductName() + " was ordered in quantity: " + orderEvent.getQuantity() + " and at a price:" + orderEvent.getPrice());
        return Mono.just(orderEvent)
                .doOnNext(event -> {
                    log.info("The product: {} was ordered in quantity: {} and at a price: {}", event.getProductName(), event.getQuantity(), event.getPrice());
                    log.info("To pay: {}", new BigDecimal(event.getQuantity()).multiply(event.getPrice()));
                });
    }
}
