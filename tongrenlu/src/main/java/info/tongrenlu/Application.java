package info.tongrenlu;

import java.util.Collections;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionTrackingMode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.WebApplicationInitializer;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableTransactionManagement
@ImportResource({ "config/spring-context.xml", "config/servlet-context.xml" })
public class Application {

    public static void main(final String[] args) {
        new SpringApplication(Application.class).run(args);
    }

    @Bean
    public SessionTrackingConfigListener sessionTrackingConfigListener() {
        final SessionTrackingConfigListener listener = new SessionTrackingConfigListener();
        return listener;
    }

    public class SessionTrackingConfigListener implements
            WebApplicationInitializer {

        @Override
        public void onStartup(final ServletContext servletContext)
                throws ServletException {

            servletContext.setSessionTrackingModes(Collections.singleton(SessionTrackingMode.COOKIE));

        }

    }
}
