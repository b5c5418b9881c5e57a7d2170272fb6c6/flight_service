package core.bankmini.flightservice.config;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
public abstract class IntegrationTest {

    @Container
    @ServiceConnection
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:17")
            .withUsername("postgres")
            .withPassword("postgres")
            .withDatabaseName("testFlightService");

}
