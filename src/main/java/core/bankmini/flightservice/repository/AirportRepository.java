package core.bankmini.flightservice.repository;

import core.bankmini.flightservice.model.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AirportRepository extends JpaRepository<Airport, UUID> {
}
