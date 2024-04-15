package org.example.notificationservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@EnableDiscoveryClient
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceApplication {

    private final EmailSenderService senderService;

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @KafkaListener(topics = "notificationTopic")
    public void handleNotification(OrderPlacedEvent orderPlacedEvent) {
        senderService.sendEmail(orderPlacedEvent.getEmail(),"Order","Thank you for placing an order on our website!\n That's your order number - " + orderPlacedEvent.getOrderNumber());
        log.info("Received notification email ----> " + orderPlacedEvent.getOrderNumber());
    }
}
