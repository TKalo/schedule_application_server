package com.coopschedulingapplication.restapiserver.Configurations;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Bean
    public DataSource datasource() {
        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://coop-scheduling-app.cbfw6bz1qy9e.us-east-2.rds.amazonaws.com:5432/postgres")
                .username("Gorrmy")
                .password("5sQrJJjKSa3RI!3Q!wlX")
                .build();
    }
}
