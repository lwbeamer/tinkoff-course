package ru.tinkoff.edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command{
    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "ввывести окно с командами";
    }


    //у команды help (только у неё, это особый случай), не вызвается этот метод. Она обрабатывается прямиком в UserMessageProcessor
    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(),"Help is executing...");
    }
}