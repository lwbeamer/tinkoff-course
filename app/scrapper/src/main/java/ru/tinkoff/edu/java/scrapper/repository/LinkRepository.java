package ru.tinkoff.edu.java.scrapper.repository;

import ru.tinkoff.edu.java.scrapper.model.Link;

import java.util.List;


public interface LinkRepository {
    List<Link> findAll();

    Link findByUrl(String url);
    void add(Link link);

    void updateCheckDate(Link link);
    void remove(Long id);
    List<Link> findOldLinks(Long timeUpdateDeltaInSeconds);

    void updateGhForksCount(Link link);

    void updateGhDescription(Link link);

    void updateGhPushedAt(Link link);

    void updateSoLastEditDate(Link link);

    void updateSoAnswerCount(Link link);
}
