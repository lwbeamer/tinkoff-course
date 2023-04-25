package ru.tinkoff.edu.java.scrapper.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import parser.LinkParser;
import result.GithubParseResult;
import result.ParseResult;
import result.StackOverflowParseResult;
import ru.tinkoff.edu.java.scrapper.client.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.model.Link;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdateService;

import java.util.List;

@Slf4j
@Component
public class LinkUpdateScheduler {

    private final LinkUpdateService linkUpdateService;



    public LinkUpdateScheduler(LinkUpdateService linkUpdateService) {
        this.linkUpdateService = linkUpdateService;
    }

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() {
        log.info("update() method invocation in LinkUpdateScheduler");
        linkUpdateService.updateLinks();
    }
}
