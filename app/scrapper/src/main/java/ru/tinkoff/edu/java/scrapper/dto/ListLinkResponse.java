package ru.tinkoff.edu.java.scrapper.dto;

import java.util.List;
import ru.tinkoff.edu.java.scrapper.model.commonDto.Link;

public record ListLinkResponse(List<Link> links, int size) {
}
