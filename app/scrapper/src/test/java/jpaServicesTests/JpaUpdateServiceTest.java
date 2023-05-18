package jpaServicesTests;

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
import ru.tinkoff.edu.java.scrapper.configuration.database.acess.JpaAccessConfiguration;
import ru.tinkoff.edu.java.scrapper.mapper.LinkRowMapper;
import ru.tinkoff.edu.java.scrapper.model.commonDto.Link;
import ru.tinkoff.edu.java.scrapper.model.jpa.LinkEntity;
import ru.tinkoff.edu.java.scrapper.service.contract.LinkUpdateService;
import ru.tinkoff.edu.java.scrapper.service.jpa.impl.JpaLinkUpdateServiceImpl;

import java.sql.Timestamp;
import java.util.List;

@SpringBootTest(properties = {"app.data-base-access-type=jpa"},classes = {ScrapperApplication.class, TestConfiguration.class, JpaAccessConfiguration.class, LinkRowMapper.class})
public class JpaUpdateServiceTest extends IntegrationEnvironment {

    @Autowired
    private LinkUpdateService linkUpdateService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private LinkRowMapper linkRowMapper;


    @Test
    @Transactional
    @Rollback
    public void getOldLinksTest(){
        List<Link> beforeAddLink = jdbcTemplate.query("select * from link", linkRowMapper);

        //добавляем "новые" ссылки
        for (int i = 0; i < 10; i++) {
            Link linkToAdd = new Link();
            linkToAdd.setUrl("https://stackoverflow.com/questions/2336692/java-multiple-class-declarations-in-one-file" + i);
            linkToAdd.setCheckedAt(new Timestamp(System.currentTimeMillis()));
            jdbcTemplate.update("insert into link (url, checked_at) values(?, ?)", linkToAdd.getUrl(), linkToAdd.getCheckedAt());
        }

        //добавляем "старые" ссылки
        for (int i = 10; i < 20; i++) {
            Link linkToAdd = new Link();
            linkToAdd.setUrl("https://stackoverflow.com/questions/2336692/java-multiple-class-declarations-in-one-file" + i);
            linkToAdd.setCheckedAt(new Timestamp(System.currentTimeMillis()-1000*60*60*100));
            jdbcTemplate.update("insert into link (url, checked_at) values(?, ?)", linkToAdd.getUrl(), linkToAdd.getCheckedAt());
        }

        List<Link> afterAddLink = jdbcTemplate.query("select * from link", linkRowMapper);


        //target
        List<Link> oldLinks = linkUpdateService.getOldLinks();

        Assertions.assertEquals(beforeAddLink.size(), 0);
        Assertions.assertEquals(afterAddLink.size(), 20);
        Assertions.assertEquals(oldLinks.size(), 10);

    }

}
