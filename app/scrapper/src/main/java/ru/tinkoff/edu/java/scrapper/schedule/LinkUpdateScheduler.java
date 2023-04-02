package ru.tinkoff.edu.java.scrapper.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Slf4j
@Component
public class LinkUpdateScheduler {


    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() {
        log.info(OffsetDateTime.now().toString());
    }
}
