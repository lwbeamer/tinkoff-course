package ru.tinkoff.edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;
import ru.tinkoff.edu.java.bot.client.ScrapperClientException;
import ru.tinkoff.edu.java.bot.dto.Link;
import ru.tinkoff.edu.java.bot.dto.ListLinkResponse;

@Component
public class StartCommand implements Command{

    private final ScrapperClient scrapperClient;

    public StartCommand(ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
    }


    @Value("${tg.bot.token}")
    private String token;


    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "зарегистрировать пользователя";
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        try {
            scrapperClient.registerChat(chatId);
            return new SendMessage(chatId, "Привет! Рад познакомиться, "+update.message().chat().firstName());
        } catch (ScrapperClientException e){
            return new SendMessage(chatId, e.getMessage());
        }



    }
}
