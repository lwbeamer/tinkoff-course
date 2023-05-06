package ru.tinkoff.edu.java.scrapper.rest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.scrapper.dto.UserAddDto;
import ru.tinkoff.edu.java.scrapper.model.commonDto.User;
import ru.tinkoff.edu.java.scrapper.service.contract.TgChatService;

@RestController
@RequestMapping("/tg-chat")
public class ChatRestController {

    private final TgChatService chatService;

    public ChatRestController(TgChatService chatService) {
        this.chatService = chatService;
    }

    //Здесь в тело запроса добавил UserDto, чтобы добавить поля username, firstName и пр.
    //id оставил в PathVariable, чтобы не менять способ обращения к endpoint'у
    @PostMapping(value = "{id}")
    public void registerChat(@PathVariable Long id, @RequestBody UserAddDto userAddDto) {
        chatService.register(new User(id, userAddDto.username(), userAddDto.firstName(), userAddDto.lastName()));
    }

    @DeleteMapping(value = "{id}")
    public void deleteChat(@PathVariable Long id) {
        chatService.unregister(id);
    }

}
