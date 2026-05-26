package core.bankmini.flightservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        SwaggerCustomConfig.class,
        CorsConfig.class,
        SecurityConfig.class
})
public class AppConfig {
}
