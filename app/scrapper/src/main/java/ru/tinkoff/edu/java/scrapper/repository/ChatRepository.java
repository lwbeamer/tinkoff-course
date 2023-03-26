package ru.tinkoff.edu.java.scrapper.repository;

import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.exception.ChatAlreadyExistException;
import ru.tinkoff.edu.java.scrapper.exception.LinkIsAlreadyAddedException;
import ru.tinkoff.edu.java.scrapper.exception.LinkNotFoundException;
import ru.tinkoff.edu.java.scrapper.exception.ChatNotFoundException;
import ru.tinkoff.edu.java.scrapper.model.Chat;
import ru.tinkoff.edu.java.scrapper.model.Link;

import java.util.*;

@Repository
public class ChatRepository {

    private final List<Chat> chats = new ArrayList<>();

    public Set<Link> getLinksByChatId(long chatId){
        return new HashSet<>(findById(chatId).getLinks());
    }

    public void deleteByChatId(long chatId){
        Chat chat = findById(chatId);
        chats.remove(chat);
    }

    public void save(long chatId){
        if (chats.stream().anyMatch(c -> c.getId() == chatId)) throw new ChatAlreadyExistException("Chat with given id already exist");
        chats.add(new Chat(chatId));
    }

    public Chat findById(long chatId){
        Optional<Chat> chat = chats.stream().filter(c -> c.getId() == chatId).findFirst();
        if (chat.isEmpty()) throw new ChatNotFoundException("No chat with given id");
        return chat.get();
    }

    public Link addLinkToChat(long chatId, String url){
        Chat chat = findById(chatId);
        if (chat.getLinks().stream().anyMatch(l -> l.getUrl().equals(url))) throw new LinkIsAlreadyAddedException("Link is already added");
        Link link = new Link(url);
        chat.getLinks().add(link);

        return link;
    }

    public Link deleteLinkFromChat(long chatId, String url){
        Chat chat = findById(chatId);
        if (chat.getLinks().stream().noneMatch(l -> l.getUrl().equals(url))) throw new LinkNotFoundException("No link with such url in given chat");
        Link link = chat.getLinks().stream().filter(l -> l.getUrl().equals(url)).findFirst().get();
        chat.getLinks().remove(link);
        return link;
    }
}
