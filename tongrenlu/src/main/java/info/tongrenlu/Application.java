package info.tongrenlu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableTransactionManagement
@ImportResource({ "config/spring-context.xml", "config/servlet-context.xml" })
public class Application {

    public static void main(final String[] args) {
        new SpringApplication(Application.class).run(args);
    }

}
