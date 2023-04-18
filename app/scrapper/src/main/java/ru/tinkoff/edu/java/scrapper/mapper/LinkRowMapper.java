package ru.tinkoff.edu.java.scrapper.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.model.Link;
import ru.tinkoff.edu.java.scrapper.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LinkRowMapper implements RowMapper<Link> {

    @Override
    public Link mapRow(ResultSet rs, int rowNum) throws SQLException {
        Link link = new Link();
        link.setId(rs.getLong("id"));
        link.setUrl(rs.getString("url"));
        link.setUpdatedAt(rs.getTimestamp("updated_at"));
        return link;
    }
}
