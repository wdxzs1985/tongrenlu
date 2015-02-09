package info.tongrenlu;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
public class Application {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public JdbcTemplate mysqlDao() {
        return new JdbcTemplate(this.mysqlDataSource());
    }

    public DataSource mysqlDataSource() {
        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://local.mysql/tongrenlu?useUnicode=yes&characterEncoding=UTF-8&autoReconnectForPools=true");
        dataSource.setUsername("tongrenlu");
        dataSource.setPassword("tongrenlu$mysql");
        return dataSource;
    }
}
