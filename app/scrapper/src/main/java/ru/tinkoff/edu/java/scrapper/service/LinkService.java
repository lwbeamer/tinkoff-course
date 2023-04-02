package ru.tinkoff.edu.java.scrapper.service;

import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.Link;
import ru.tinkoff.edu.java.scrapper.repository.ChatRepository;

import java.util.List;

@Service
public class LinkService {


    private final ChatRepository chatRepository;

    public LinkService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public List<Link> getLinks(long chatId) {
        return chatRepository.getLinksByChatId(chatId).stream().toList();
    }

    public Link addLink(Long chatId, AddLinkRequest request) {
        return chatRepository.addLinkToChat(chatId, request.link());
    }

    public Link deleteLink(Long chatId, RemoveLinkRequest request) {
        return chatRepository.deleteLinkFromChat(chatId, request.link());
    }
}
