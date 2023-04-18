package ru.tinkoff.edu.java.scrapper.service.jdbc;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.model.Link;
import ru.tinkoff.edu.java.scrapper.model.Relation;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.SubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.service.SubscriptionService;

import java.net.URI;
import java.sql.Timestamp;
import java.util.List;


@Service
public class JdbcSubscriptionService implements SubscriptionService {


    private final LinkRepository linkRepository;

    private final SubscriptionRepository subscriptionRepository;


//    private final long TWENTY_YEARS_IN_MILLIS = 20L * 365 * 24 * 60 * 60 * 1000;

    public JdbcSubscriptionService(LinkRepository linkRepository, SubscriptionRepository subscriptionRepository) {
        this.linkRepository = linkRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    @Transactional
    public Link add(Long chatId, URI url) {
        Link link = linkRepository.findByUrl(url.toString());
        if (link == null) {
            link = new Link();
            link.setUrl(url.toString());
            link.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            linkRepository.add(link);
            //обращение ниже нужно, чтобы получить id из БД
            link = linkRepository.findByUrl(link.getUrl());
        }
        Relation relation = subscriptionRepository.findSubscription(link.getId(), chatId);

        if (relation == null) {
            relation = new Relation();
            relation.setChatId(chatId);
            relation.setLinkId(link.getId());
            subscriptionRepository.addRelation(relation);
        }
        return link;
    }

    @Override
    @Transactional
    public Link remove(Long chatId, URI url) {
        Link link = linkRepository.findByUrl(url.toString());

        if (link != null) {
//            linkRepository.remove(link.getId());
            subscriptionRepository.remove(link.getId(), chatId);
        }
        return link;
    }

    @Override
    public List<Link> getAllByUser(Long chatId) {
        return subscriptionRepository.findLinksByChat(chatId);
    }
}
