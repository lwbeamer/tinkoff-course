package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import parser.LinkParser;
import result.GithubParseResult;
import result.ParseResult;
import result.StackOverflowParseResult;
import ru.tinkoff.edu.java.scrapper.client.BotClient;
import ru.tinkoff.edu.java.scrapper.client.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.dto.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.dto.StackOverflowItem;
import ru.tinkoff.edu.java.scrapper.exception.GitHubRequestException;
import ru.tinkoff.edu.java.scrapper.exception.StackOverflowRequestException;
import ru.tinkoff.edu.java.scrapper.model.Link;
import ru.tinkoff.edu.java.scrapper.model.Relation;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.SubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdateService;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@Slf4j
public class JdbcLinkUpdateService implements LinkUpdateService {


    @Value("${update.delta.time}")
    private Long timeUpdateDeltaInSeconds;

    private final LinkRepository linkRepository;

    private final SubscriptionRepository subscriptionRepository;

    private final LinkParser linkParser;

    private final GitHubClient gitHubClient;

    private final StackOverflowClient stackOverflowClient;

    private final BotClient botClient;


    public JdbcLinkUpdateService(LinkRepository linkRepository, SubscriptionRepository subscriptionRepository, LinkParser linkParser, GitHubClient gitHubClient, StackOverflowClient stackOverflowClient, BotClient botClient) {
        this.linkRepository = linkRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.linkParser = linkParser;
        this.gitHubClient = gitHubClient;
        this.stackOverflowClient = stackOverflowClient;
        this.botClient = botClient;
    }

    @Override
    public List<Link> getOldLinks() {
        return linkRepository.findOldLinks(timeUpdateDeltaInSeconds);
    }


    public void updateLinks() {
        List<Link> oldLinks = getOldLinks();

        for (Link link : oldLinks) {
            ParseResult result = linkParser.parseUrl(link.getUrl());
            if (result instanceof GithubParseResult) {
                try {
                    System.out.println(link.getUrl());
                    GitHubResponse response = gitHubClient.fetchRepo(((GithubParseResult) result).username(), ((GithubParseResult) result).repository());
                    System.out.println("github = " + link.getUrl() + " " + response.updatedAt() + " " + response.pushedAt());
                    System.out.println("Дата обновления из АПИ: "+ response.updatedAt().toInstant());
                    System.out.println("Дата обновления из БД: "+ link.getUpdatedAt().toInstant());
                    if (response.updatedAt().toInstant().isAfter(link.getUpdatedAt().toInstant())) {
                        link.setUpdatedAt(new Timestamp(response.updatedAt().toInstant().toEpochMilli()));
                        linkRepository.updateDate(link);
                        Long[] chats = subscriptionRepository.findChatsByLink(link.getId()).stream().map(Relation::getChatId).toArray(Long[]::new);
                        botClient.updateLink(new LinkUpdate(link.getId(), link.getUrl(), "some udate of gh link", chats));
                    }
                } catch (GitHubRequestException e) {
                    log.warn(e.getMessage());
                }

            } else if (result instanceof StackOverflowParseResult) {
                try {
                    System.out.println(link.getUrl());
                    StackOverflowItem response = stackOverflowClient.fetchQuestion(((StackOverflowParseResult) result).id());
                    System.out.println("stackover = " + link.getUrl() + " " + response.lastActivityDate());
                    if (response.lastActivityDate().isAfter(link.getUpdatedAt().toLocalDateTime().atOffset(ZoneOffset.UTC))) {
                        link.setUpdatedAt(new Timestamp(response.lastActivityDate().toInstant().toEpochMilli()));
                        linkRepository.updateDate(link);
                        Long[] chats = subscriptionRepository.findChatsByLink(link.getId()).stream().map(Relation::getChatId).toArray(Long[]::new);
                        botClient.updateLink(new LinkUpdate(link.getId(), link.getUrl(), "some udate of so link", chats));
                    }

                } catch (StackOverflowRequestException e) {
                    log.warn(e.getMessage());
                }
            }
        }
    }
}
