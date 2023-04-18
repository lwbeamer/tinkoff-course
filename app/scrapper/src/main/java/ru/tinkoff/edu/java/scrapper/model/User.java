package ru.tinkoff.edu.java.scrapper.model;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {

    public User(Long chatId, String username, String firstName, String lastName) {
        this.chatId = chatId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private Long chatId;
    private String username;
    private String firstName;
    private String lastName;
}
