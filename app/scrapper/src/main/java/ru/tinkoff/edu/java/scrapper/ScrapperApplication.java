package ru.tinkoff.edu.java.scrapper;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.model.jpa.LinkEntity;
import ru.tinkoff.edu.java.scrapper.service.ScrapperQueueProducer;
import ru.tinkoff.edu.java.scrapper.service.jpa.impl.JpaSubscriptionServiceImpl;
import ru.tinkoff.edu.java.scrapper.service.jpa.impl.JpaTgChatServiceImpl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
@EnableScheduling
public class ScrapperApplication {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(ScrapperApplication.class, args);
        ApplicationConfig config = ctx.getBean(ApplicationConfig.class);

        System.out.println("КОНФИГ: "+config.queueName()+" "+config.exchangeName()+" "+config.routingKey()+" "+config.useQueue());

//        ScrapperQueueProducer sender = ctx.getBean(ScrapperQueueProducer.class);

//        sender.send(new LinkUpdate(10L,"some url","some description",new Long[]{1L,20L,42L}));


    }
}