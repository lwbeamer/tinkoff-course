package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.bot.commands.*;
import ru.tinkoff.edu.java.bot.telegram.Bot;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class BotConfiguration {

    @Value("${tg.bot.token}")
    private String token;

    @Value("${tg.api.baseUrl}")
    private String tgApiBaseUrl;

    private final HelpCommand helpCommand;
    private final StartCommand startCommand;
    private final ListCommand listCommand;
    private final TrackCommand trackCommand;
    private final UntrackCommand untrackCommand;

    public BotConfiguration(HelpCommand helpCommand, StartCommand startCommand, ListCommand listCommand, TrackCommand trackCommand, UntrackCommand untrackCommand) {
        this.helpCommand = helpCommand;
        this.startCommand = startCommand;
        this.listCommand = listCommand;
        this.trackCommand = trackCommand;
        this.untrackCommand = untrackCommand;
    }


    @Bean
    public Bot bot() {
        List<Command> commands = new ArrayList<>();

        commands.add(helpCommand);
        commands.add(listCommand);
        commands.add(startCommand);
        commands.add(trackCommand);
        commands.add(untrackCommand);


        //Делаем кнопку "Меню" рядом с иконкой скрепки
        //нужно отправить запрос на API телеграмма (метод setMyCommands) со списком команд в виде List<BotCommand>
        List<BotCommand> apiCommands = new ArrayList<>(commands.stream().map(Command::toApiCommand).toList());
        WebClient botConfClient = WebClient.create(tgApiBaseUrl+token);
        botConfClient.post().uri("/setMyCommands").bodyValue(new SetCommandRequest(apiCommands)).retrieve()
                .bodyToMono(String.class)
                .block();

        return new Bot(token, commands);
    }

}
