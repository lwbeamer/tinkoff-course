package ru.tinkoff.edu.java.scrapper.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Link {


    private Long id;
    private String url;
    private Timestamp checkedAt;
    private Timestamp ghPushedAt;
    private String ghDescription;
    private int ghForksCount;
    private Timestamp soLastEditDate;
    private int soAnswerCount;



}
