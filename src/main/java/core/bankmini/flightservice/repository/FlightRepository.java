package core.bankmini.flightservice.repository;

import core.bankmini.flightservice.model.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FlightRepository extends JpaRepository<Flight, UUID> {

//    @Query("""
//    select new core.bankmini.flightservice.model.entity.Flight (
//    f.id, f.flightNumber, f.airline, f.aircraft, f.departureAirportCode,
//    f.
//    )
//    from Flight f
//    join
//""")
//    List<Flight> findAllByDepartureAndArrivalAndDepartureDate(String from, String to, LocalDate date);
}
