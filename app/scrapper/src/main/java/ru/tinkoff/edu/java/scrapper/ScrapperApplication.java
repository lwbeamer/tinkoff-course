package ru.tinkoff.edu.java.scrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
@EnableScheduling
public class ScrapperApplication {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(ScrapperApplication.class, args);
        ApplicationConfig config = ctx.getBean(ApplicationConfig.class);


//        ScrapperQueueProducer sender = ctx.getBean(ScrapperQueueProducer.class);
//        for (int i = 1; i < 10; i++){
//            sender.updateLink(new LinkUpdate(10L,"some url","рэбит робит чи как",new Long[]{488021427L}));
//            Thread.sleep(1000);
//        }
    }
}