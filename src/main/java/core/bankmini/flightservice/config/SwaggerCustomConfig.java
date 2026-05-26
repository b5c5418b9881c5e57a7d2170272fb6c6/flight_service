package core.bankmini.flightservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerCustomConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        Info info = new Info()
                .title("Ticket Service API")
                .version("v1.0.0")
                .description("REST API for ticket processing system")
                .contact(new Contact()
                        .name("ProjectEx Team")
                        .email("test@mail.ru"))
                .license(new License()
                        .name("Apache 2.0"));

        return new OpenAPI()
                .info(info);
    }
}
