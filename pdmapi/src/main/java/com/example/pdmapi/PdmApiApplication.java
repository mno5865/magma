package com.example.pdmapi;

import com.jcraft.jsch.*;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.Scanner;

import org.ini4j.Ini;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PdmApiApplication {

    public static void main(String[] args) {

        int lport = 5432;
        String rhost = "starbug.cs.rit.edu";
        int rport = 5432;

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

        String databaseName = "p32001_08";

        String driverName = "org.postgresql.Driver";
        Connection conn = null;
        Session session = null;
        try {
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            session = jsch.getSession(user, rhost, 22);
            session.setPassword(pass);
            session.setConfig(config);
            session.setConfig("PreferredAuthentications","publickey,keyboard-interactive,password");
            session.connect();
            System.out.println("Connected");
            int assigned_port = session.setPortForwardingL(lport, "localhost", rport);
            System.out.println("Port Forwarded");

            // Assigned port could be different from 5432 but rarely happens
            String url = "jdbc:postgresql://localhost:"+ assigned_port + "/" + databaseName;

            System.out.println("database Url: " + url);
            Properties props = new Properties();
            props.put("user", user);
            props.put("password", pass);

            Class.forName(driverName);
            conn = DriverManager.getConnection(url, props);
            System.out.println("Database connection established");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SpringApplication.run(PdmApiApplication.class, args);
            //removed so that db & ssh tunnel remain open while api is running
        }
    }
}
