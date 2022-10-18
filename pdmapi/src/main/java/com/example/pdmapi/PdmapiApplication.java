package com.example.pdmapi;

import com.jcraft.jsch.*;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PdmapiApplication {

    public static void main(String[] args) {

        int lport = 5432;
        String rhost = "starbug.cs.rit.edu";
        int rport = 5432;

        Scanner input = new Scanner(System.in);
        System.out.println("Enter your username:");

        String in = input.next();
        String user = in;
        System.out.println("Enter your password:");

        in = input.next();
        String password = in;
        input.close();
        String databaseName = "p32001_08";

        String driverName = "org.postgresql.Driver";
        Connection conn = null;
        Session session = null;
        try {
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            session = jsch.getSession(user, rhost, 22);
            session.setPassword(password);
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
            props.put("password", password);

            Class.forName(driverName);
            conn = DriverManager.getConnection(url, props);
            System.out.println("Database connection established");

            // Do something with the database....
            SpringApplication.run(PdmapiApplication.class, args);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boolean close = false;
            try {
                close = conn.isClosed();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (conn != null && !close) {
                System.out.println("Closing Database Connection");
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (session != null && session.isConnected()) {
                System.out.println("Closing SSH Connection");
                session.disconnect();
            }
        }
    }


}
