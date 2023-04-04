package ru.tinkoff.edu.java.bot.telegram;

import com.pengrad.telegrambot.model.Update;
import ru.tinkoff.edu.java.bot.commands.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserMessageProcessor {

    private final List<Command> commands;

    // Состояние пользователя. Нужно для удобной работы с командами /track и /untrack
    // TYPING_COMMAND - состояние ввода команды (по-умолчанию)
    // TYPING_TRACKED - состояние ввода ссылки для добавления
    // TYPING_UNTRACKED - состояние ввода ссылки для удаления
    private final Map<Long, UserState> userStateMap;


    public UserMessageProcessor(List<Command> commands) {
        this.commands = commands;
        userStateMap = new HashMap<>();
    }

    public String process(Update update) {
        Command command;
        switch (update.message().text()) {
            case "/start" -> {
                userStateMap.put(update.message().chat().id(), UserState.TYPING_COMMAND);
                command = commands.stream().filter(c -> c instanceof StartCommand).findFirst().get();
                return command.handle(update);
            }
            case "/track" -> {
                userStateMap.put(update.message().chat().id(), UserState.TYPING_TRACKED);
                return "Отправьте ссылку, которую хотите начать отслеживать";
            }
            case "/untrack" -> {
                userStateMap.put(update.message().chat().id(), UserState.TYPING_UNTRACKED);
                return "Отправьте ссылку, которую хотите перестать отслеживать";
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
                return text.toString();
            }
            default -> {
                long chatId = update.message().chat().id();
                if (userStateMap.get(chatId).equals(UserState.TYPING_TRACKED)) {
                    userStateMap.put(chatId, UserState.TYPING_COMMAND);
                    return commands.stream().filter(c -> c instanceof TrackCommand).findFirst().get().handle(update);
                } else if (userStateMap.get(chatId).equals(UserState.TYPING_UNTRACKED)) {
                    userStateMap.put(chatId, UserState.TYPING_COMMAND);
                    return commands.stream().filter(c -> c instanceof UntrackCommand).findFirst().get().handle(update);
                } else
                    return "Неизвестная команда. Нажмите 'Меню' чтобы посмотреть список доступных команд";
            }

        }


    }


}
