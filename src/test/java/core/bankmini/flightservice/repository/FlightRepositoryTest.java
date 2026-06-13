package core.bankmini.flightservice.repository;

import core.bankmini.flightservice.config.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(TestcontainersConfiguration.class)
class FlightRepositoryTest {

    @Autowired
    FlightRepository flightRepository;

    @Test
    void searchAllFlightsWithDeparture() {
    }

    @Test
    void searchAllFlightsWithArrival() {
    }

    @Test
    void searchAllFlightsWithoutDate() {
    }

    @Test
    void searchAllFlightsWithDate() {
    }

    @Test
    void findByFlightNumber() {
    }

    @Test
    void cancelExpiredFlights() {
    }

    @Test
    void existsByFlightNumberIs() {
    }
}