package com.tutrit.testcontainerdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;

@SpringBootTest
@Import(value = MySQLContainerTest.class)
class MyEntityRepoTest extends MySQLContainerTest {

    @Autowired
    MyEntityRepo myEntityRepo;

    @Autowired
    DataSource dataSource;

    @Test
    void test() {
        myEntityRepo.save(new MyEntity());
        Iterable<MyEntity> result = myEntityRepo.findAll();
        result.forEach(System.out::println);
        jdbcTemplate().execute("SELECT  1");
        jdbcTemplate1().execute("SELECT 1");
        jdbcTemplate2().execute("SELECT 1");
        printDatabases(dataSource);
        printDatabases(db1());
        printDatabases(db2());
    }

    private JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    private DataSource db1() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.testcontainers.jdbc.ContainerDatabaseDriver");
        dataSourceBuilder.url("jdbc:tc:mysql://localhost:3306/db1");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("1234");
        return dataSourceBuilder.build();
    }

    private JdbcTemplate jdbcTemplate1() {
        return new JdbcTemplate(db1());
    }

    private DataSource db2() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.testcontainers.jdbc.ContainerDatabaseDriver");
        dataSourceBuilder.url("jdbc:tc:mysql://localhost:3306/db2");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("1234");
        return dataSourceBuilder.build();
    }

    private JdbcTemplate jdbcTemplate2() {
        return new JdbcTemplate(db2());
    }

    private void printDatabases(DataSource ds) {
        try {
            ResultSet rs = ds.getConnection().createStatement().executeQuery("show databases");
            System.out.println("Database list");
            while (rs.next()) {
                System.out.println("\t"+rs.getString((1)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}