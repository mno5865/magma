/**
 * File: ApiConfig.java
 * ApiConfig.java: A public class that gets the Data Source from the API and does error handling.
 * @author MAGMA
 */
package com.example.pdmapi.Config;

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
            String str = System.getProperty("user.dir");
            String path;
            if (str.substring(str.length()-5).equals("magma")) {
                path = "pdmapi/src/main/java/com/example/pdmapi/Service/dbInfo.ini";
            } else {
                path = "src/main/java/com/example/pdmapi/Service/dbInfo.ini";
            }
            Ini ini = new Ini(new File(path));
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
}
