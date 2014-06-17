package info.tongrenlu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.CookieGenerator;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static void main(final String[] args) {
        new SpringApplication(Application.class).run(args);
    }

    @Bean
    public CookieGenerator autoLoginCookieGenerator() {
        final CookieGenerator cookieGenerator = new CookieGenerator();
        cookieGenerator.setCookieName("auto_login");
        cookieGenerator.setCookieMaxAge(604800);
        return cookieGenerator;
    }
}
