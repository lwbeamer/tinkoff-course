package ru.tinkoff.edu.java.bot.commands;

import java.util.HashMap;
import java.util.Map;

public enum CommandsEnum {

    START("/start"),
    HELP("/help"),
    LIST("/list"),
    TRACK("/track"),
    UNTRACK("/untrack");

    private static final Map<String, CommandsEnum> BY_LABEL = new HashMap<>();

    //Эта мапа инициализируется один раз при старте приложения.
    //Чтобы быстро брать инстанс CommandEnum по строковому представлению команды
    static {
        for (CommandsEnum c : values()) {
            BY_LABEL.put(c.label, c);
        }
    }

    private final String label;

    CommandsEnum(String command) {
        this.label = command;
    }

    public static CommandsEnum valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }

}
