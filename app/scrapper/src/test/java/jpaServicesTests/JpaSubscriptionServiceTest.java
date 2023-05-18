package jpaServicesTests;

import environment.IntegrationEnvironment;
import environment.TestConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.tinkoff.edu.java.scrapper.ScrapperApplication;
import ru.tinkoff.edu.java.scrapper.configuration.database.acess.JpaAccessConfiguration;
import ru.tinkoff.edu.java.scrapper.mapper.LinkRowMapper;
import ru.tinkoff.edu.java.scrapper.mapper.SubscriptionRowMapper;
import ru.tinkoff.edu.java.scrapper.model.commonDto.Link;
import ru.tinkoff.edu.java.scrapper.model.jdbcAndJooq.Relation;
import ru.tinkoff.edu.java.scrapper.model.commonDto.User;
import ru.tinkoff.edu.java.scrapper.model.jpa.LinkEntity;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.service.contract.SubscriptionService;
import ru.tinkoff.edu.java.scrapper.service.jpa.impl.JpaSubscriptionServiceImpl;


import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.List;


@SpringBootTest(properties = {"app.data-base-access-type=jpa"},classes = {ScrapperApplication.class, TestConfiguration.class, JpaAccessConfiguration.class, LinkRowMapper.class, SubscriptionRowMapper.class})
public class JpaSubscriptionServiceTest extends IntegrationEnvironment {

    @Autowired
    private LinkRowMapper linkRowMapper;

    @Autowired
    private SubscriptionRowMapper subscriptionRowMapper;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /*
    т.к. Hibernate flush'ит данные в таблицы в конце транзакции, поэтому если пометить
    тесты аннотацией Transactional, то внутри теста будет обращение к данным, которые ещё в таблицу
    не были сохранены. Из-за этого я убрал Transactional над тестами, как это было дял jdbc и jooq
    и очищаю таблицы после каждого теста вручную - откатываюсь в начальное состояние
     */
    @AfterEach
    public void clearDatabase(){
        jdbcTemplate.update("delete from user_link");
        jdbcTemplate.update("delete from link");
        jdbcTemplate.update("delete from \"user\"");
    }


    @Test
    public void subscribeTest() throws URISyntaxException {
        jdbcTemplate.update("insert into \"user\" (chat_id, username, first_name, last_name) values(?, ?, ?, ?)", 42L, "robtop21", "Robert", "Polson");

        subscriptionService.subscribe(42L,new URI("https://stackoverflow.com/questions/2336692/java-multiple-class-declarations-in-one-file"));

        List<Link> links =  jdbcTemplate.query("select * from link", linkRowMapper);

        System.out.println(links);
        Assertions.assertEquals(links.size(),1);

        List<Relation> relations = jdbcTemplate.query("select * from user_link", subscriptionRowMapper);

        System.out.println(relations);

        Assertions.assertEquals(relations.get(0).getChatId(),42L);
        Assertions.assertEquals(links.get(0).getUrl(),"https://stackoverflow.com/questions/2336692/java-multiple-class-declarations-in-one-file");
    }

    @Test
    public void unsubscribeTest() throws URISyntaxException {

        //добавляем пользователя, ссылку и подписку вручную
        jdbcTemplate.update("insert into \"user\" (chat_id, username, first_name, last_name) values(?, ?, ?, ?)", 42L, "robtop21", "Robert", "Polson");
        jdbcTemplate.update("insert into link (url, checked_at) values(?, ?)", "https://stackoverflow.com/questions/2336692/java-multiple-class-declarations-in-one-file", new Timestamp(System.currentTimeMillis()));

        List<Link> links =  jdbcTemplate.query("select * from link", linkRowMapper);


        jdbcTemplate.update("insert into user_link (link_id, chat_id) values (?,?)", links.get(0).getId(), 42L);

        List<Relation> relationsBeforeUnsubscribe = jdbcTemplate.query("select * from user_link", subscriptionRowMapper);

        //таргет
        subscriptionService.unsubscribe(42L,new URI("https://stackoverflow.com/questions/2336692/java-multiple-class-declarations-in-one-file"));

        List<Relation> relationsAfterUnsubscribe = jdbcTemplate.query("select * from user_link", subscriptionRowMapper);

        Assertions.assertEquals(relationsBeforeUnsubscribe.size(),1);
        Assertions.assertEquals(relationsAfterUnsubscribe.size(),0);
    }


    @Test
    public void getAllByUserTest(){

        List<Link> beforeAddLink = jdbcTemplate.query("select * from link inner join user_link rel on link.id = rel.link_id where rel.chat_id = ?", linkRowMapper, 42L);

        jdbcTemplate.update("insert into \"user\" (chat_id, username, first_name, last_name) values(?, ?, ?, ?)", 42L, "robtop21", "Robert", "Polson");
        jdbcTemplate.update("insert into \"user\" (chat_id, username, first_name, last_name) values(?, ?, ?, ?)", 50L, "alucard", "Will", "Smith");

        for (int i = 0; i < 20; i++) {
            Link linkToAdd = new Link();
            linkToAdd.setUrl("https://stackoverflow.com/questions/2336692/java-multiple-class-declarations-in-one-file" + i);
            linkToAdd.setCheckedAt(new Timestamp(System.currentTimeMillis()));
            jdbcTemplate.update("insert into link (url, checked_at) values(?, ?)", linkToAdd.getUrl(), linkToAdd.getCheckedAt());
        }

        List<Link> afterInsertionLink = jdbcTemplate.query("select * from link", linkRowMapper);


        for (int i = 0; i < 10; i++) {
            jdbcTemplate.update("insert into user_link (link_id, chat_id) values(?, ?)", afterInsertionLink.get(i).getId(), 42L);
        }

        //таргет
        List<Link> userLinks = subscriptionService.getLinksByChat(42L);

        Assertions.assertEquals(beforeAddLink.size(), 0);
        Assertions.assertEquals(afterInsertionLink.size(), 20);
        Assertions.assertEquals(userLinks.size(), 10);
    }


    @Test
    public void getChatsIdsByLink(){
        List<Relation> beforeAddRelation = jdbcTemplate.query("select * from user_link", subscriptionRowMapper);


        for (int i = 0; i < 10; i++) {
            User user = new User(42L + i, "robtop21" + i, "Robert", "Polson");

            jdbcTemplate.update("insert into \"user\" (chat_id, username, first_name, last_name) values(?, ?, ?, ?)",
                    user.getChatId(),
                    user.getUsername(),
                    user.getFirstName(),
                    user.getLastName());
        }

        Link linkToAdd = new Link();
        linkToAdd.setUrl("https://stackoverflow.com/questions/2336692/java-multiple-class-declarations-in-one-file");
        linkToAdd.setCheckedAt(new Timestamp(System.currentTimeMillis()));
        jdbcTemplate.update("insert into link (url, checked_at) values(?, ?)", linkToAdd.getUrl(), linkToAdd.getCheckedAt());

        List<Link> afterInsertionLink = jdbcTemplate.query("select * from link", linkRowMapper);


        for (int i = 0; i < 7; i++) {
            jdbcTemplate.update("insert into user_link (link_id, chat_id) values (?,?)",afterInsertionLink.get(0).getId(),42L+i);
        }

        //target
        List<Long> chatIds = subscriptionService.getChatIdsByLink(afterInsertionLink.get(0).getId());

        Assertions.assertEquals(beforeAddRelation.size(), 0);
        Assertions.assertArrayEquals(new Long[]{42L,43L,44L,45L,46L,47L,48L},chatIds.toArray());
    }
}
