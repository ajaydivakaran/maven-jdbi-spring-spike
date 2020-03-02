package me.spike.factory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DBIFactory {

    private final String dbUserName;
    private final String dbPassword;
    private final String dbUrl;
    private final int dbMaxPoolSize;

    private static HikariDataSource dataSource;

    public DBIFactory(@Value("${db.user}") String dbUserName,
                      @Value("${db.password}") String dbPassword,
                      @Value("${db.url}") String dbUrl,
                      @Value("${db.maxPoolSize}") int dbMaxPoolSize) {
        this.dbUserName = dbUserName;
        this.dbPassword = dbPassword;
        this.dbUrl = dbUrl;
        this.dbMaxPoolSize = dbMaxPoolSize;
    }

    public Jdbi create() {
        HikariDataSource dataSource = getDataSource();
        return Jdbi.create(dataSource);
    }

    public static void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

    private HikariDataSource getDataSource() {
        synchronized (this) {
            if (dataSource == null) {
                dataSource = createDataSource();
            }
            return dataSource;
        }
    }

    private HikariDataSource createDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        config.setUsername(dbUserName);
        config.setPassword(dbPassword);
        config.setMaximumPoolSize(dbMaxPoolSize);
        return new HikariDataSource(config);
    }
}
