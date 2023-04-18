package ru.tinkoff.edu.java.scrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.UserRepository;
import ru.tinkoff.edu.java.scrapper.repository.jooq.LinkJooqRepository;
import ru.tinkoff.edu.java.scrapper.repository.jooq.UserJooqRepository;


@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
//TODO убрал для теста, потм вернуть обратно
@EnableScheduling
public class ScrapperApplication {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(ScrapperApplication.class, args);

        LinkRepository linkRepository = ctx.getBean(LinkJooqRepository.class);
        UserRepository userRepository = ctx.getBean(UserJooqRepository.class);

//        System.out.println(userRepository.findAll().toString());
//        System.out.println(linkRepository.findAll().toString());
//        System.out.println(linkRepository.findByUrl("fd"));
//        System.out.println(linkRepository.findByUrl("https://github.com/susaasus1/MusicService").getGhDescription());

    }
}