package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.model.User;

public interface TgChatService {

    void register(User user);

    void unregister(Long chatId);





}
