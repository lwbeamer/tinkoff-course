package ru.tinkoff.edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import parser.LinkParser;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;
import ru.tinkoff.edu.java.bot.client.ScrapperClientException;
import ru.tinkoff.edu.java.bot.dto.AddLinkRequest;
import ru.tinkoff.edu.java.bot.dto.RemoveLinkRequest;

@Component
public class UntrackCommand implements Command{

    private final ScrapperClient scrapperClient;


    public UntrackCommand(ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
    }

    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "прекратить отслеживание ссылки";
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        String msg;
        try {
            if (new LinkParser().parseUrl(update.message().text()) != null){
                scrapperClient.deleteLink(chatId, new RemoveLinkRequest(update.message().text()));
                msg = "Ссылка успешно удалена";
            } else msg = "Некорректная ссылка";
            return new SendMessage(chatId, msg);
        } catch (ScrapperClientException e) {
            return new SendMessage(chatId, e.getMessage());
        }
    }
}
