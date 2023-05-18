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
import ru.tinkoff.edu.java.scrapper.mapper.UserRowMapper;
import ru.tinkoff.edu.java.scrapper.model.commonDto.User;
import ru.tinkoff.edu.java.scrapper.model.jpa.UserEntity;
import ru.tinkoff.edu.java.scrapper.service.contract.TgChatService;
import ru.tinkoff.edu.java.scrapper.service.jpa.impl.JpaTgChatServiceImpl;

import java.util.List;

@SpringBootTest(properties = {"app.data-base-access-type=jpa"},classes = {ScrapperApplication.class, TestConfiguration.class, JpaAccessConfiguration.class, UserRowMapper.class})
public class JpaTgChatServiceTest extends IntegrationEnvironment {

    @Autowired
    private TgChatService tgChatService;

    @Autowired
    private UserRowMapper userRowMapper;

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
    public void registerTest(){

        List<User> beforeAddUser = jdbcTemplate.query("select * from \"user\"", userRowMapper);


        User user = new User();
        user.setChatId(42L);
        user.setUsername("lwbeamer");
        user.setFirstName("Robert");
        user.setLastName("Polson");

        //target
        tgChatService.register(user);

        List<User> afterAddUser = jdbcTemplate.query("select * from \"user\"", userRowMapper);

        Assertions.assertEquals(beforeAddUser.size(), 0);
        Assertions.assertEquals(afterAddUser.size(), 1);
        Assertions.assertNotNull(afterAddUser.get(0));
        Assertions.assertEquals(afterAddUser.get(0).getChatId(), 42L);

    }

    @Test
    public void unregisterTest(){
        List<User> beforeAddUser = jdbcTemplate.query("select * from \"user\"", userRowMapper);

        User user = new User(42L, "robtop21", "Robert","Polson");


        jdbcTemplate.update("insert into \"user\" (chat_id, username, first_name, last_name) values(?, ?, ?, ?)",
                user.getChatId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName());


        List<User> afterAddUser = jdbcTemplate.query("select * from \"user\"", userRowMapper);

        //target
        tgChatService.unregister(42L);

        List<User> afterRemoveUser = jdbcTemplate.query("select * from \"user\"", userRowMapper);


        Assertions.assertEquals(beforeAddUser.size(), 0);
        Assertions.assertEquals(afterAddUser.size(), 1);
        Assertions.assertNotNull(afterAddUser.get(0));
        Assertions.assertEquals(afterAddUser.get(0).getChatId(), 42L);
        Assertions.assertEquals(afterRemoveUser.size(), 0);
    }


}
