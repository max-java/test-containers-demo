package com.tutrit.testcontainerdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//@Configuration
public class MySQLContainerTest {
    static {

        GenericContainer container = new MySQLContainer(DockerImageName.parse("mysql:latest"))
                .withUsername("root")
                .withPassword("1234")
                .withDatabaseName("mydb")
                .withInitScript("init.sql")
                .withExposedPorts(3306)
                .withEnv("MYSQL_ROOT_HOST", "%");

        List<String> portBindings = new ArrayList<>();
        portBindings.add("3310:3306"); // hostPort:containerPort
        container.setPortBindings(portBindings);

        container.start();


        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mysql://localhost:3310/mydb");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("1234");
        DataSource dataSource = dataSourceBuilder.build();
        try {
            dataSource.getConnection().createStatement().execute("SELECT  1");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    @Autowired
    DataSource dataSource;

//    @Bean
    public GenericContainer genericContainer() {
        List<String> portBindings = new ArrayList<>();
        portBindings.add("3310:3306"); // hostPort:containerPort
        GenericContainer container = new MySQLContainer(DockerImageName.parse("mysql:latest"))
                .withUsername("root")
                .withPassword("1234")
                .withDatabaseName("mydb")
                .withInitScript("init.sql")
                .withExposedPorts(3306)
                .withEnv("MYSQL_ROOT_HOST", "%");
        container.setPortBindings(portBindings);
        return container;
    }

//    @Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mysql://localhost:3310/mydb");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("1234");
        return dataSourceBuilder.build();
    }
}
