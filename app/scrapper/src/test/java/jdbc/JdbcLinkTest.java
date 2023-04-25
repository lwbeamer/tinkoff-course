package jdbc;

import environment.IntegrationEnvironment;
import environment.TestConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.ScrapperApplication;
import ru.tinkoff.edu.java.scrapper.configuration.database.acess.JdbcAccessConfiguration;
import ru.tinkoff.edu.java.scrapper.mapper.LinkRowMapper;
import ru.tinkoff.edu.java.scrapper.model.commonDto.Link;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.LinkJdbcTemplateRepository;
import ru.tinkoff.edu.java.scrapper.repository.jdbcAndJooqContract.LinkRepository;

import java.sql.Timestamp;
import java.util.List;

@SpringBootTest(classes = {ScrapperApplication.class, TestConfiguration.class, JdbcAccessConfiguration.class})
public class JdbcLinkTest extends IntegrationEnvironment {

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private LinkRowMapper linkRowMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    @Transactional
    @Rollback
    public void addLinkTest() {
        List<Link> beforeAddLink = jdbcTemplate.query("select * from link where link.url='https://stackoverflow.com/questions/2336692/java-multiple-class-declarations-in-one-file'", linkRowMapper);

        Link linkToAdd = new Link();
        linkToAdd.setUrl("https://stackoverflow.com/questions/2336692/java-multiple-class-declarations-in-one-file");
        linkToAdd.setCheckedAt(new Timestamp(System.currentTimeMillis()));
        linkRepository.add(linkToAdd);

        List<Link> addedLink = jdbcTemplate.query("select * from link where link.url='https://stackoverflow.com/questions/2336692/java-multiple-class-declarations-in-one-file'", linkRowMapper);

        Assertions.assertEquals(beforeAddLink.size(), 0);
        Assertions.assertNotNull(addedLink.get(0));
        Assertions.assertEquals("https://stackoverflow.com/questions/2336692/java-multiple-class-declarations-in-one-file", addedLink.get(0).getUrl());
    }


    @Test
    @Transactional
    @Rollback
    public void removeLinkTest() {
        List<Link> beforeAddLink = jdbcTemplate.query("select * from link where link.url='https://stackoverflow.com/questions/2336692/java-multiple-class-declarations-in-one-file'", linkRowMapper);

        Link linkToAdd = new Link();
        linkToAdd.setUrl("https://stackoverflow.com/questions/2336692/java-multiple-class-declarations-in-one-file");
        linkToAdd.setCheckedAt(new Timestamp(System.currentTimeMillis()));
        jdbcTemplate.update("insert into link (url, checked_at) values(?, ?)", linkToAdd.getUrl(), linkToAdd.getCheckedAt());

        List<Link> afterInsertionLink = jdbcTemplate.query("select * from link where link.url='https://stackoverflow.com/questions/2336692/java-multiple-class-declarations-in-one-file'", linkRowMapper);

        linkRepository.remove(afterInsertionLink.get(0).getId());

        List<Link> afterRemovingLink = jdbcTemplate.query("select * from link where link.url='https://stackoverflow.com/questions/2336692/java-multiple-class-declarations-in-one-file'", linkRowMapper);


        Assertions.assertEquals(beforeAddLink.size(), 0);
        Assertions.assertEquals(afterInsertionLink.size(), 1);
        Assertions.assertEquals(afterRemovingLink.size(), 0);
    }


    @Test
    @Transactional
    @Rollback
    public void findAllTest() {
        List<Link> beforeAddLink = jdbcTemplate.query("select * from link", linkRowMapper);

        for (int i = 0; i < 10; i++) {
            Link linkToAdd = new Link();
            linkToAdd.setUrl("https://stackoverflow.com/questions/2336692/java-multiple-class-declarations-in-one-file" + i);
            linkToAdd.setCheckedAt(new Timestamp(System.currentTimeMillis()));
            jdbcTemplate.update("insert into link (url, checked_at) values(?, ?)", linkToAdd.getUrl(), linkToAdd.getCheckedAt());
        }

        List<Link> afterInsertionLink = linkRepository.findAll();

        Assertions.assertEquals(beforeAddLink.size(), 0);
        Assertions.assertEquals(afterInsertionLink.size(), 10);
    }


    @Test
    @Transactional
    @Rollback
    public void findByUrlTest(){
        List<Link> beforeAddLink = jdbcTemplate.query("select * from link", linkRowMapper);

        for (int i = 0; i < 10; i++) {
            Link linkToAdd = new Link();
            linkToAdd.setUrl("https://stackoverflow.com/questions/2336692/java-multiple-class-declarations-in-one-file" + i);
            linkToAdd.setCheckedAt(new Timestamp(System.currentTimeMillis()));
            jdbcTemplate.update("insert into link (url, checked_at) values(?, ?)", linkToAdd.getUrl(), linkToAdd.getCheckedAt());
        }

        List<Link> afterInsertionLink = jdbcTemplate.query("select * from link", linkRowMapper);
        Link foundedByUrlLink = linkRepository.findByUrl("https://stackoverflow.com/questions/2336692/java-multiple-class-declarations-in-one-file0");

        Assertions.assertEquals(beforeAddLink.size(), 0);
        Assertions.assertEquals(afterInsertionLink.size(), 10);
        Assertions.assertNotNull(foundedByUrlLink);
    }


    @Test
    @Transactional
    @Rollback
    public void updateDateTest(){
        List<Link> beforeAddLink = jdbcTemplate.query("select * from link", linkRowMapper);


        Link linkToAdd = new Link();
        linkToAdd.setUrl("https://stackoverflow.com/questions/2336692/java-multiple-class-declarations-in-one-file");
        Timestamp timestampBefore = new Timestamp(System.currentTimeMillis());
        linkToAdd.setCheckedAt(timestampBefore);
        jdbcTemplate.update("insert into link (url, checked_at) values(?, ?)", linkToAdd.getUrl(), linkToAdd.getCheckedAt());


        List<Link> linkBeforeUpdate = jdbcTemplate.query("select * from link where link.url = ?", linkRowMapper, "https://stackoverflow.com/questions/2336692/java-multiple-class-declarations-in-one-file");

        Assertions.assertEquals(linkBeforeUpdate.get(0).getCheckedAt(),timestampBefore);

        linkBeforeUpdate.get(0).setCheckedAt(new Timestamp(100000));

        linkRepository.updateCheckDate(linkBeforeUpdate.get(0));

        List<Link> linkAfterUpdate = jdbcTemplate.query("select * from link where link.url = ?", linkRowMapper, "https://stackoverflow.com/questions/2336692/java-multiple-class-declarations-in-one-file");

        Assertions.assertEquals(beforeAddLink.size(), 0);
        Assertions.assertEquals(linkAfterUpdate.get(0).getCheckedAt(),new Timestamp(100000));
    }





}
