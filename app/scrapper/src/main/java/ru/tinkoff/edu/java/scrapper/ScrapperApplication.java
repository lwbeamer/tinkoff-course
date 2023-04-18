package ru.tinkoff.edu.java.scrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.model.Relation;
import ru.tinkoff.edu.java.scrapper.repository.SubscriptionJdbcTemplateRepository;


@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
@EnableScheduling
public class ScrapperApplication {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(ScrapperApplication.class, args);
//        ApplicationConfig config = ctx.getBean(ApplicationConfig.class);
//        System.out.println(config);

//        System.out.println(ctx.getBean(GitHubClient.class).fetchRepo("lwbeamer","asm-like-language"));
//        System.out.println(ctx.getBean(StackOverflowClient.class).fetchQuestion(2336692));


//        SubscriptionJdbcTemplateRepository userLinkJdbcTemplateRepository = ctx.getBean(SubscriptionJdbcTemplateRepository.class);


//        Relation relation = new Relation();

//        userLink.setLinkId(1L);
//        userLink.setChatId(334L);
//
//        userLinkJdbcTemplateRepository.add(userLink);
//
//        userLink.setLinkId(3L);
//        userLink.setChatId(228L);
//
//        userLinkJdbcTemplateRepository.add(userLink);
//
//
//        userLink.setLinkId(3L);
//        userLink.setChatId(229L);
//
//        userLinkJdbcTemplateRepository.add(userLink);

//        System.out.println(userLinkJdbcTemplateRepository.findAll());

//        userLinkJdbcTemplateRepository.remove(3L,228L);

//        System.out.println(userLinkJdbcTemplateRepository.findAll());



//        LinkJdbcTemplateRepository linkRepository = ctx.getBean(LinkJdbcTemplateRepository.class);
//
//        Link link = new Link();
//
//        link.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
//        link.setUrl("https://stackoverflow.com/questions/5102/how-do-you-set-up-python-scripts-to-work-in-apache-2-0/");

//        linkRepository.add(link);

//        System.out.println(linkRepository.findAll().toString());
//
//        linkRepository.remove(2L);
//
//        System.out.println(linkRepository.findAll().toString());


//        System.out.println(userRepository.findAll().toString());

//        User userToAdd = new User();
//        userToAdd.setChatId(229L);
//        userToAdd.setUsername("ebl7an");
//        userToAdd.setFirstName("lolkek");
//        userToAdd.setLastName("cheburek");
//
//        userRepository.add(userToAdd);
//
//        userRepository.remove(534L);
    }
}