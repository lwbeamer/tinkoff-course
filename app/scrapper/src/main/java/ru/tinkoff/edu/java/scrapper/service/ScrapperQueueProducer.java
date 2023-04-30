package ru.tinkoff.edu.java.scrapper.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdate;

@Slf4j
public class ScrapperQueueProducer implements UpdateNotificationService{

    private final AmqpTemplate rabbitTemplate;

    @Value("${app.exchange-name}")
    private String exchange;

    @Value("${app.routing-key}")
    private String routingKey;


    public ScrapperQueueProducer(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void updateLink(LinkUpdate update){
        rabbitTemplate.convertAndSend(exchange, routingKey, update);
        log.info("UpdateMessage "+update+" has been sent");
    }
}
