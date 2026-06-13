package core.bankmini.flightservice.repository;

import core.bankmini.flightservice.model.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AirlineRepository extends JpaRepository<Airline, UUID> {
    Airline findByIataCode(String iataCode);

    Airline findByIcaoCode(String icaoCode);
}
