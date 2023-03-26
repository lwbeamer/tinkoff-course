package ru.tinkoff.edu.java.scrapper.rest;

import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.Link;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.util.List;

@RestController
@RequestMapping("/links")
public class LinkRestController {


    private final LinkService linkService;

    public LinkRestController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping
    public ListLinkResponse getLinks(@RequestHeader("Tg-Chat-Id") Long chatId){
        List<Link> list = linkService.getLinks(chatId);
        return new ListLinkResponse(list,list.size());
    }

    @PostMapping
    public LinkResponse addLink(@RequestHeader("Tg-Chat-Id") Long chatId, @RequestBody AddLinkRequest request){
        Link link = linkService.addLink(chatId, request);
        return new LinkResponse(link.getId(), link.getUrl());
    }

    @DeleteMapping
    public LinkResponse deleteLink(@RequestHeader("Tg-Chat-Id") Long chatId, @RequestBody RemoveLinkRequest request){
        Link link = linkService.deleteLink(chatId, request);
        return new LinkResponse(link.getId(), link.getUrl());
    }


}
