package ru.tinkoff.edu.java.scrapper.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.service.contract.LinkUpdateService;

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
