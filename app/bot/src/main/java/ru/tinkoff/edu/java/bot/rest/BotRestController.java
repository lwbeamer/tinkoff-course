package ru.tinkoff.edu.java.bot.rest;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.dto.LinkUpdate;
import ru.tinkoff.edu.java.bot.service.UpdateService;

@RestController
public class BotRestController {


    private final UpdateService updateService;


    public BotRestController(UpdateService updateService) {
        this.updateService = updateService;
    }

    @PostMapping("updates")
    public void sendUpdate(@RequestBody LinkUpdate request){
        updateService.updateLink(request);
    }



}
