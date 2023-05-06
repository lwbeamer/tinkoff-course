package ru.tinkoff.edu.java.scrapper.model.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.sql.Timestamp;

@Entity
@Table(name = "link")
@Data
public class LinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url", unique = true, nullable = false)
    private String url;

    @Column(name = "checked_at", nullable = false)
    private Timestamp checkedAt;

    @Column(name = "gh_pushed_at")
    private Timestamp ghPushedAt;

    @Column(name = "gh_description")
    private String ghDescription;

    @Column(name = "gh_forks_count")
    private Integer ghForksCount;

    @Column(name = "so_last_edit_date")
    private Timestamp soLastEditDate;

    @Column(name = "so_answer_count")
    private Integer soAnswerCount;

}
