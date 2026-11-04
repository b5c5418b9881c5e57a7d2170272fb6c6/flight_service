package core.bankmini.flightservice.repository;

import core.bankmini.flightservice.model.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AirlineRepository extends JpaRepository<Airline, UUID> {
    Airline findByIataCode(String iataCode);

    Airline findByIcaoCode(String icaoCode);

    @Query("""
                select a
                from Airline a
                where (:iataCode is null or a.iataCode = :iataCode)
                  and (:icaoCode is null or a.icaoCode = :icaoCode)
                  and (:name is null or a.name = :name)
            """)
    List<Airline> searchAirlinesByQueries(
            @Param("iataCode") String iataCode,
            @Param("icaoCode") String icaoCode,
            @Param("name") String name
    );

    boolean existsByIataCodeAndIcaoCode(String iataCode, String icaoCode);
}
