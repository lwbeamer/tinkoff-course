package ru.tinkoff.edu.java.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;
import ru.tinkoff.edu.java.bot.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.bot.dto.AddLinkRequest;
import ru.tinkoff.edu.java.bot.dto.RemoveLinkRequest;
import ru.tinkoff.edu.java.bot.telegram.Bot;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class BotApplication {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(BotApplication.class, args);
        ApplicationConfig config = ctx.getBean(ApplicationConfig.class);


        ScrapperClient client = ctx.getBean(ScrapperClient.class);

        Bot bot = ctx.getBean(Bot.class);

        bot.start();

//        System.out.println(client.getLinks(3L));
//        System.out.println(client.addLink(3L, new AddLinkRequest("http://someNewLink")));
//        System.out.println(client.getLinks(3L));
//        System.out.println(client.deleteLink(3L,new RemoveLinkRequest("http://someNewLink")));
//        System.out.println(client.getLinks(3L));
//
//        client.registerChat(10L);
//        client.addLink(10L, new AddLinkRequest("http://someNewLink"));
//        System.out.println(client.getLinks(10L));
//
//        client.deleteChat(10L);
//        System.out.println(client.getLinks(10L));


    }
}
