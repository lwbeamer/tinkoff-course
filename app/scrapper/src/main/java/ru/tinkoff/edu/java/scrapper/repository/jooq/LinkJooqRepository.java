package ru.tinkoff.edu.java.scrapper.repository.jooq;

import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.LinkRecord;
import ru.tinkoff.edu.java.scrapper.model.Link;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;

import java.sql.Timestamp;
import java.util.List;


import static ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Link.*;

@Repository
@Slf4j
public class LinkJooqRepository implements LinkRepository {


    private final DSLContext dslContext;



    public LinkJooqRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public List<Link> findAll() {
        log.info("findAll() method invocation in linkJooqRepo");
        return dslContext.selectFrom(LINK)
                .fetchInto(Link.class);
    }

    @Override
    public Link findByUrl(String url) {
        log.info("findByUrl() method invocation in linkJooqRepo");
        return dslContext.selectFrom(LINK)
                .where(LINK.URL.eq(url))
                .fetchOneInto(Link.class);
    }

    @Override
    public void add(Link link) {
        log.info("add() method invocation in linkJooqRepo");
        dslContext.insertInto(LINK)
                .set(LINK.URL, link.getUrl())
                .set(LINK.CHECKED_AT, link.getCheckedAt().toLocalDateTime())
                .execute();
    }

    @Override
    public void updateCheckDate(Link link) {
        log.info("updateDate() method invocation in linkJooqRepo");
        dslContext.update(LINK)
                .set(LINK.CHECKED_AT, link.getCheckedAt().toLocalDateTime())
                .where(LINK.ID.eq(link.getId()))
                .execute();
    }

    @Override
    public void remove(Long id) {
        log.info("remove() method invocation in linkJooqRepo");
        dslContext.deleteFrom(LINK)
                .where(LINK.ID.eq(id))
                .execute();
    }

    @Override
    public List<Link> findOldLinks(Long timeUpdateDeltaInSeconds) {
        log.info("findOldLinks() method invocation in linkJooqRepo");
        Timestamp compareDate = new Timestamp(System.currentTimeMillis() - timeUpdateDeltaInSeconds * 1000);
        return dslContext.selectFrom(LINK)
                .where(LINK.CHECKED_AT.lessThan(compareDate.toLocalDateTime()))
                .orderBy(LINK.CHECKED_AT.desc())
                .fetchInto(Link.class);
    }

    @Override
    public void updateGhForksCount(Link link) {
        log.info("updateGhForksCount() method invocation in linkJooqRepo");
        dslContext.update(LINK)
                .set(LINK.GH_FORKS_COUNT, link.getGhForksCount())
                .where(LINK.ID.eq(link.getId()))
                .execute();

    }

    @Override
    public void updateGhDescription(Link link) {
        log.info("updateGhDescription() method invocation in linkJooqRepo");
        dslContext.update(LINK)
                .set(LINK.GH_DESCRIPTION, link.getGhDescription())
                .where(LINK.ID.eq(link.getId()))
                .execute();

    }

    @Override
    public void updateGhPushedAt(Link link) {
        log.info("updateGhPushedAt() method invocation in linkJooqRepo");
        dslContext.update(LINK)
                .set(LINK.GH_PUSHED_AT, link.getGhPushedAt().toLocalDateTime())
                .where(LINK.ID.eq(link.getId()))
                .execute();

    }

    @Override
    public void updateSoLastEditDate(Link link) {
        log.info("updateSoLastEditDate() method invocation in linkJooqRepo");
        dslContext.update(LINK)
                .set(LINK.SO_LAST_EDIT_DATE, link.getSoLastEditDate().toLocalDateTime())
                .where(LINK.ID.eq(link.getId()))
                .execute();
    }

    @Override
    public void updateSoAnswerCount(Link link) {
        log.info("updateSoAnswerCount() method invocation in linkJooqRepo");
        dslContext.update(LINK)
                .set(LINK.SO_ANSWER_COUNT, link.getSoAnswerCount())
                .where(LINK.ID.eq(link.getId()))
                .execute();
    }
}
