package edu.bbte.idde.mnim2165.repository.jdbc;

import edu.bbte.idde.mnim2165.exception.RepositoryException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
@Profile("jdbc")
public class ConnectionManager {
    private final List<Connection> pool = new LinkedList<>();

    @Value("${jdbcDriverClass}")
    private String driverClass;

    @Value("${jdbcDbUrl}")
    private String dbUrl;

    @Value("${jdbcUserName}")
    private String username;

    @Value("${jdbcUserPassword}")
    private String password;

    @Value("${poolSize}")
    private Integer poolSize;

    @PostConstruct
    protected void init() {
        try {
            log.info("driver class is: " + driverClass);
            Class.forName(driverClass);
            log.info("Class loading succesful");
        } catch (ClassNotFoundException e) {
            log.error("Class loading failed", e);
            throw new RepositoryException("Class loading failed", e);
        }

        log.info(String.valueOf(poolSize));
        for (int i = 0; i < poolSize; i++) {
            try {
                pool.add(DriverManager.getConnection(dbUrl, username, password));

            } catch (java.sql.SQLException e) {
                log.error("Connection Failed", e);
            }
        }
    }

    public synchronized Connection getConnection() {
        if (!pool.isEmpty()) {
            return pool.remove(0);
        }
        return null;
    }

    public synchronized void returnConnection(Connection connection) {
        if (pool.size() < poolSize) {
            pool.add(connection);
        }
    }
}
