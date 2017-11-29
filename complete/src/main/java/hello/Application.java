package hello;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/*@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        //SpringApplication.run(Application.class, args);
        SpringApplication application = new SpringApplication(Application.class);

        Map<String, Object> map = new HashMap<>();
        map.put("SERVER_PORT", "7070");
        application.setDefaultProperties(map);
        application.run(args);
    }
}
*/
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
public class Application extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return super.configure(builder);
    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.run(args);
    }
}
