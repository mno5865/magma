package com.example.pdmapi.Config;

import com.zaxxer.hikari.HikariConfig;
import org.ini4j.Ini;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

@Configuration
public class ApiConfig {

    @Bean
    public DataSource getDataSource() {
        String user;
        String pass;

        try{
            Ini ini = new Ini(new File("src/main/java/com/example/pdmapi/Service/dbInfo.ini"));
            user = ini.get("header", "username");
            pass = ini.get("header", "password");
        } catch (IOException e){
            Scanner input = new Scanner(System.in);
            System.out.println("Enter Username:");
            user = input.next();
            System.out.println("Enter Password:");
            pass = input.next();
            input.close();
        }

        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url("jdbc:postgresql://localhost:5432/p32001_08");
        dataSourceBuilder.username(user);
        dataSourceBuilder.password(pass);
        return dataSourceBuilder.build();
    }

    @Bean
    public HikariConfig config() {
        HikariConfig hikariConfig = new HikariConfig();

        // other setting
        hikariConfig.addDataSourceProperty("socketTimeout", 600000);
        hikariConfig.setMaxLifetime(600000);

        return hikariConfig;
    }
}
