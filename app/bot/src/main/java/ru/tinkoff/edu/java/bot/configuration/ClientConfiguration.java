package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;


@Configuration
public class ClientConfiguration {

    @Value("${scrapper.baseurl}")
    private String scrapperBaseUrl;



    //Регистрируем клиентов как бины
    @Bean
    public ScrapperClient scrapperClient() {
        return new ScrapperClient(scrapperBaseUrl);
    }
}
