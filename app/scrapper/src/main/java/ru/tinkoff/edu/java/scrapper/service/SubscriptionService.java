package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.model.Link;

import java.net.URI;
import java.util.List;

public interface SubscriptionService {

    Link add(Long chatId, URI url);
    Link remove(Long chatId, URI url);

    List<Link> getAllByUser(Long chatId);

}
