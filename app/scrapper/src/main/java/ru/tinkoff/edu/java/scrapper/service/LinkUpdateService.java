package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.model.Link;

import java.util.List;

public interface LinkUpdateService {


    List<Link> getOldLinks();

    void updateLinks();
}
