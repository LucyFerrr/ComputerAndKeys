package org.ksa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Configuration class for setting up the application's {@link DataSource}.
 */
@Configuration
public class DataSourceConfig {

    /**
     * Creates and configures a {@link DataSource} bean using environment variables.
     *
     * @return a configured {@link DataSource} instance for MySQL
     */
    @Bean
    public DataSource dataSource() {
        String url = System.getenv("SPRING_DATASOURCE_URL");
        String userName = System.getenv("SPRING_DATASOURCE_USERNAME");
        String password = System.getenv("SPRING_DATASOURCE_PASSWORD");
        String driver = System.getenv("SPRING_DATASOURCE_DRIVER_CLASS_NAME");

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driver);

        return dataSource;
    }
}
