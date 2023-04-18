package ru.tinkoff.edu.java.scrapper.repository;

import ru.tinkoff.edu.java.scrapper.model.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();

    User findByChatId(Long id);
    void add(User user);
    void remove(Long id);

}
