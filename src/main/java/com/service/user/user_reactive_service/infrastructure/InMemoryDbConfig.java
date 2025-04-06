package com.service.user.user_reactive_service.infrastructure;

import org.h2.tools.Server;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.sql.SQLException;

@Profile({"default", "dev", "test"})
@Configuration
@EnableR2dbcRepositories
public class InMemoryDbConfig {

    private Server h2WebServer;

    @EventListener(ApplicationStartedEvent.class)
    public void onApplicationStart() throws SQLException {
        this.h2WebServer = Server.createWebServer("-webPort", "9090").start();
    }

    @EventListener(ContextClosedEvent.class)
    public void onApplicationStop() throws SQLException {
        this.h2WebServer.stop();
    }

//    @Bean
//    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
//        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
//        initializer.setConnectionFactory(connectionFactory);
//        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
//        return initializer;
//    }
}
