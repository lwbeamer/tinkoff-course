package ru.tinkoff.edu.java.scrapper.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.exception.ChatAlreadyExistException;
import ru.tinkoff.edu.java.scrapper.model.User;
import ru.tinkoff.edu.java.scrapper.repository.SubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.repository.UserRepository;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

@Service
public class TgChatServiceImpl implements TgChatService {

    private final UserRepository userRepository;

    private final SubscriptionRepository subscriptionRepository;


    public TgChatServiceImpl(UserRepository userRepository, SubscriptionRepository subscriptionRepository) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public void register(User user) {
        User userInBd = userRepository.findByChatId(user.getChatId());
        if (userInBd != null) throw new ChatAlreadyExistException();
        userRepository.add(user);
    }

    @Override
    @Transactional
    public void unregister(Long chatId) {
        userRepository.remove(chatId);
        subscriptionRepository.removeAllByUser(chatId);
    }

}
