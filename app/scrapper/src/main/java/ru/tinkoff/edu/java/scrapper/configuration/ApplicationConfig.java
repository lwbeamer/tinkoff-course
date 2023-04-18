package ru.tinkoff.edu.java.scrapper.configuration;


import jakarta.validation.constraints.NotNull;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.edu.java.scrapper.schedule.Scheduler;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(@NotNull String test, Scheduler scheduler) {

    @Bean
    public long schedulerIntervalMs(ApplicationConfig config) {
        return config.scheduler().interval().toMillis();
    }



//    @Bean
//    public DefaultConfigurationCustomizer postgresJooqCustomizer() {
//        return (DefaultConfiguration c) -> c.settings()
//                .withRenderSchema(false)
//                .withRenderFormatted(true)
//                .withRenderQuotedNames(RenderQuotedNames.ALWAYS);
//    }


    //    @Value("${spring.datasource.username}")
//    private String userName;
//
//    @Value("${spring.datasource.password}")
//    private String password;
//
//    @Value("${jdbc:postgresql://localhost:5432/scrapper}")
//    private String url;
//
//    @Bean
//    public DSLContext dslContext() throws SQLException {
//        Connection conn = DriverManager.getConnection(url, userName, password);
//        return DSL.using(conn, SQLDialect.POSTGRES);
//    }
}

