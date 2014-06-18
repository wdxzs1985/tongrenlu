package info.tongrenlu;

import info.tongrenlu.domain.UserBean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableTransactionManagement
@ImportResource({ "config/spring-context.xml",
                 "config/servlet-context.xml",
                 "config/mail.xml",
                 "config/constants.xml" })
public class Application {

    public static void main(final String[] args) {
        new SpringApplication(Application.class).run(args);
    }

    @Bean
    public UserBean guestUserbean() {
        final UserBean guestUserbean = new UserBean();
        guestUserbean.setId(0);
        return guestUserbean;
    }

}
