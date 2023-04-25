package ru.tinkoff.edu.java.scrapper.service.jdbcAndJooq.impl;

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
import ru.tinkoff.edu.java.scrapper.model.commonDto.Link;
import ru.tinkoff.edu.java.scrapper.model.jdbcAndJooq.Relation;
import ru.tinkoff.edu.java.scrapper.repository.jdbcAndJooqContract.LinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jdbcAndJooqContract.SubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.service.contract.LinkUpdateService;

import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
public class LinkUpdateServiceImpl implements LinkUpdateService {


    @Value("${update.delta.time}")
    private Long timeUpdateDeltaInSeconds;

    private final LinkRepository linkRepository;

    private final SubscriptionRepository subscriptionRepository;

    private final LinkParser linkParser;

    private final GitHubClient gitHubClient;

    private final StackOverflowClient stackOverflowClient;

    private final BotClient botClient;


    public LinkUpdateServiceImpl(LinkRepository linkRepository, SubscriptionRepository subscriptionRepository, LinkParser linkParser, GitHubClient gitHubClient, StackOverflowClient stackOverflowClient, BotClient botClient) {
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
                    boolean isUpdated = false;
                    String updateDescription = "";

                    GitHubResponse response = gitHubClient.fetchRepo(((GithubParseResult) result).username(), ((GithubParseResult) result).repository());


                    if (response.forksCount() != link.getGhForksCount()) {
                        isUpdated = true;
                        if (response.forksCount() < link.getGhForksCount()) {
                            updateDescription += "В репозитории уменьшилось кол-во форков\n";
                        }
                        if (response.forksCount() > link.getGhForksCount()) {
                            updateDescription += "В репозитории появились новые форки\n";
                        }
                        link.setGhForksCount(response.forksCount());
                    }


                    if (link.getGhDescription() == null || !response.description().equals(link.getGhDescription())) {
                        if (link.getGhDescription() != null) isUpdated = true;
                        link.setGhDescription(response.description());
                        updateDescription += "В репозитории изменилось описание\n";
                    }

                    if (link.getGhPushedAt() == null || response.pushedAt().toInstant().isAfter(link.getGhPushedAt().toInstant())) {
                        if (link.getGhPushedAt() != null) isUpdated = true;
                        link.setGhPushedAt(new Timestamp(response.pushedAt().toInstant().toEpochMilli()));
                        updateDescription += "В репозитории появился новый commit\n";
                    }


                    linkRepository.updateGhLink(link);

                    if (isUpdated) {
                        Long[] chats = subscriptionRepository.findChatsByLink(link.getId()).stream().map(Relation::getChatId).toArray(Long[]::new);
                        botClient.updateLink(new LinkUpdate(link.getId(), link.getUrl(), "Вышли обновления в репозитории:\n" + updateDescription, chats));
                    }


                } catch (GitHubRequestException e) {
                    log.warn(e.getMessage());
                }

            } else if (result instanceof StackOverflowParseResult) {
                try {

                    boolean isUpdated = false;
                    String updateDescription = "";

                    StackOverflowItem response = stackOverflowClient.fetchQuestion(((StackOverflowParseResult) result).id());

                    if (response.lastEditDate() != null && (link.getSoLastEditDate() == null || response.lastEditDate().isAfter(link.getSoLastEditDate().toLocalDateTime().atOffset(ZoneOffset.UTC)))) {
                        if (link.getSoLastEditDate() != null) isUpdated = true;
                        link.setSoLastEditDate(new Timestamp(response.lastEditDate().toInstant().toEpochMilli()));
                        updateDescription += "Текст вопроса был изменён\n";
                    }

                    if (response.answerCount() != link.getSoAnswerCount()) {
                        isUpdated = true;
                        if (response.answerCount() < link.getSoAnswerCount()) {
                            updateDescription += "На вопрос уменьшилось кол-во ответов\n";
                        }
                        if (response.answerCount() > link.getSoAnswerCount()) {
                            updateDescription += "На вопрос появились новые ответы\n";
                        }
                        link.setSoAnswerCount(response.answerCount());
                    }

                    linkRepository.updateSoLink(link);

                    if (isUpdated) {
                        Long[] chats = subscriptionRepository.findChatsByLink(link.getId()).stream().map(Relation::getChatId).toArray(Long[]::new);
                        botClient.updateLink(new LinkUpdate(link.getId(), link.getUrl(), "Вышли обновления в вопросе:\n" + updateDescription, chats));
                    }

                } catch (StackOverflowRequestException e) {
                    log.warn(e.getMessage());
                }
            }
        }
    }
}
