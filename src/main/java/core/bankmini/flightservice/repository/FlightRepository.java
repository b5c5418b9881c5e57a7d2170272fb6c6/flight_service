package core.bankmini.flightservice.repository;

import core.bankmini.flightservice.model.entity.Flight;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FlightRepository extends JpaRepository<Flight, UUID> {

    @EntityGraph(attributePaths = {
            "departureAirport",
            "arrivalAirport"
    })
    @Query("""
                select f
                from Flight f
                where f.departureAirport.iataCode = :fromAirport
                and f.arrivalAirport.iataCode = :toAirport
                and f.departureAt >= :start
                and f.departureAt < :end
            """)
    List<Flight> searchAllFlightsWithDeparture(
            @Param("fromAirport") String fromAirport,
            @Param("toAirport") String toAirport,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {
            "departureAirport",
            "arrivalAirport"
    })
    @Query("""
                select f
                from Flight f
                where f.departureAirport.iataCode = :fromAirport
                and f.arrivalAirport.iataCode = :toAirport
                and f.arrivalAt >= :start
                and f.arrivalAt < :end
            """)
    List<Flight> searchAllFlightsWithArrival(
            @Param("fromAirport") String fromAirport,
            @Param("toAirport") String toAirport,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {
            "departureAirport",
            "arrivalAirport"
    })
    @Query("""
                select f
                from Flight f
                where f.departureAirport.iataCode = :fromAirport
                and f.arrivalAirport.iataCode = :toAirport
            """)
    List<Flight> searchAllFlightsWithoutDate(
            @Param("fromAirport") String fromAirport,
            @Param("toAirport") String toAirport,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {
            "departureAirport",
            "arrivalAirport"
    })
    @Query("""
                select f
                from Flight f
                where f.departureAirport.iataCode = :fromAirport
                and f.arrivalAirport.iataCode = :toAirport
                and f.departureAt >= :startDeparture
                and f.departureAt < :endDeparture
                and f.arrivalAt >= :startArrival
                and f.arrivalAt < :endArrival
            """)
    List<Flight> searchAllFlightsWithDate(
            @Param("fromAirport") String fromAirport,
            @Param("toAirport") String toAirport,
            @Param("startDeparture") LocalDateTime startDeparture,
            @Param("endDeparture") LocalDateTime endDeparture,
            @Param("startArrival") LocalDateTime startArrival,
            @Param("endArrival") LocalDateTime endArrival,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {
            "departureAirport",
            "arrivalAirport"
    })
    @Query("""
                select f
                from Flight f
                join fetch f.aircraft
                join fetch f.airline
                where f.flightNumber = :flightNumber
            """)
    Flight findByFlightNumber(String flightNumber);

    @Modifying
    @Query("""
                update Flight f
                set f.status = 'CANCELLED'
                where f.status = 'CREATED'
                  and f.departureAt < :now
            """)
    int cancelExpiredFlights(@Param("now") LocalDateTime now);

    Optional<Flight> existsByFlightNumberIs(String flightNumber);
}
