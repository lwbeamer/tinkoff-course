package ru.tinkoff.edu.java.bot.telegram;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;
import ru.tinkoff.edu.java.bot.commands.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserMessageProcessor {

    private List<Command> commands;
    private Map<Long, UserState> userStateMap;


    public UserMessageProcessor(List<Command> commands) {
        this.commands = commands;
        userStateMap = new HashMap<>();
    }

    SendMessage process(Update update) {
        Command command;
        switch (update.message().text()) {
            case "/start" -> {
                userStateMap.put(update.message().chat().id(), UserState.TYPING_COMMAND);
                command = commands.stream().filter(c -> c instanceof StartCommand).findFirst().get();
                return command.handle(update);
            }
            case "/track" -> {
                userStateMap.put(update.message().chat().id(), UserState.TYPING_TRACKED);
                return new SendMessage(update.message().chat().id(), "Отправьте ссылку, которую хотите начать отслеживать");
            }
            case "/untrack" -> {
                userStateMap.put(update.message().chat().id(), UserState.TYPING_UNTRACKED);
                return new SendMessage(update.message().chat().id(), "Отправьте ссылку, которую хотите перестать отслеживать");
            }
            case "/list" -> {
                userStateMap.put(update.message().chat().id(), UserState.TYPING_COMMAND);
                command = commands.stream().filter(c -> c instanceof ListCommand).findFirst().get();
                return command.handle(update);
            }
            case "/help" -> {
                userStateMap.put(update.message().chat().id(), UserState.TYPING_COMMAND);
                StringBuilder text = new StringBuilder();
                for (Command c : commands) {
                    text.append(c.command()).append(" - ").append(c.description()).append("\n");
                }
                return new SendMessage(update.message().chat().id(), text.toString());
            }
            default -> {
                long chatId = update.message().chat().id();
                if (userStateMap.get(chatId).equals(UserState.TYPING_TRACKED)){
                    userStateMap.put(chatId, UserState.TYPING_COMMAND);
                    return commands.stream().filter(c -> c instanceof TrackCommand).findFirst().get().handle(update);
                }
                else if (userStateMap.get(chatId).equals(UserState.TYPING_UNTRACKED)){
                    userStateMap.put(chatId, UserState.TYPING_COMMAND);
                    return commands.stream().filter(c -> c instanceof UntrackCommand).findFirst().get().handle(update);
                }
                else
                    return new SendMessage(chatId, "Неизвестная команда. Нажмите 'Меню' чтобы посмотреть список доступных команд");
            }

        }


    }


}
