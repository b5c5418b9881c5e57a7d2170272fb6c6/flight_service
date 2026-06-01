package core.bankmini.flightservice.repository;

import core.bankmini.flightservice.model.entity.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AircraftRepository extends JpaRepository<Aircraft, UUID> {
}
