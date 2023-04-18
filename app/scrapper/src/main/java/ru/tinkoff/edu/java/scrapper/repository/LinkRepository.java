package ru.tinkoff.edu.java.scrapper.repository;

import ru.tinkoff.edu.java.scrapper.model.Link;
import ru.tinkoff.edu.java.scrapper.model.User;

import java.util.List;


public interface LinkRepository {
    List<Link> findAll();

    Link findByUrl(String url);
    void add(Link link);

    void updateDate(Link link);
    void remove(Long id);
    List<Link> findOldLinks(Long timeUpdateDeltaInSeconds);

}
