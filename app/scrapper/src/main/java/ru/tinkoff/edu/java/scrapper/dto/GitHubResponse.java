package ru.tinkoff.edu.java.scrapper.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record GitHubResponse(@JsonProperty("pushed_at") OffsetDateTime pushedAt,
                             @JsonProperty("updated_at") OffsetDateTime updatedAt,
                             @JsonProperty("full_name") String fullName) {

}