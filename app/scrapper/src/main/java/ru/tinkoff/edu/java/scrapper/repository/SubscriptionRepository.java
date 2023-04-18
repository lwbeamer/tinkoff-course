package ru.tinkoff.edu.java.scrapper.repository;

import ru.tinkoff.edu.java.scrapper.model.Link;
import ru.tinkoff.edu.java.scrapper.model.Relation;
import ru.tinkoff.edu.java.scrapper.model.User;

import java.util.List;

public interface SubscriptionRepository {

    List<Link> findLinksByChat(Long chatId);

    List<Relation> findChatsByLink(Long linkId);

    Relation findSubscription(Long linkId, Long chatId);
    void addRelation(Relation relation);
    void remove(Long linkId, Long chatId);

    void removeAllByUser(Long chatId);
}
