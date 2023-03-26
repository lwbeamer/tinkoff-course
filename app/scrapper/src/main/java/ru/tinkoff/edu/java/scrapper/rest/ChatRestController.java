package ru.tinkoff.edu.java.scrapper.rest;

import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.service.ChatService;

@RestController
@RequestMapping("/tg-chat")
public class ChatRestController {


    private final ChatService chatService;

    public ChatRestController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping(value = "{id}")
    public void registerChat(@PathVariable Long id){
        chatService.registerChat(id);
    }

    @DeleteMapping(value = "{id}")
    public void deleteChat(@PathVariable Long id){
        chatService.deleteChat(id);
    }



}
