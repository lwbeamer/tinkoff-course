package ru.tinkoff.edu.java.bot.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SetCommandRequest(@JsonProperty("commands") List<BotCommand> commands) {
}
