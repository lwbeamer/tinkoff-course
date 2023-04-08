package ru.tinkoff.edu.java.bot.telegram;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import ru.tinkoff.edu.java.bot.commands.Command;
import ru.tinkoff.edu.java.bot.commands.CommandsEnum;

import java.util.EnumMap;

public class Bot implements AutoCloseable {

    private final TelegramBot bot;
    private final UserMessageProcessor userMessageProcessor;



    @PostConstruct
    public void init(){
        start();
    }

    public Bot(String token, EnumMap<CommandsEnum, Command> commands) {
        System.out.println("Создание бота... Токен: " + token);
        userMessageProcessor = new UserMessageProcessor(commands);
        bot = new TelegramBot(token);
    }

    public void start() {
        System.out.println("Бот запущен...");
        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                bot.execute(new SendMessage(update.message().chat().id(), userMessageProcessor.process(update)));

//                System.out.println(update.message().text());
//                long chatId = update.message().chat().id();
//                SendResponse response = bot.execute(new SendMessage(chatId, "Hello!"));
//                Message reply = update.message().replyToMessage();
//                if (reply != null)
//                    System.out.println("Ответ на такое сообщение: " + reply.text() + " такого содержания: " + update.message().text());
//                update.message().replyMarkup();


            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }


    @Override
    public void close() throws Exception {
        bot.removeGetUpdatesListener();
    }
}
