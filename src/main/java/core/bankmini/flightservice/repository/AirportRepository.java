package core.bankmini.flightservice.repository;

import core.bankmini.flightservice.model.entity.Airport;
import core.bankmini.flightservice.model.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AirportRepository extends JpaRepository<Airport, UUID> {

    @Query("""
                select a
                from Airport a
                where (:country is null or a.country = :country)
                  and (:city is null or a.city = :city)
                  and (:iataCode is null or a.iataCode = :iataCode)
            """)
    List<Airport> searchAirportsByQueries(
            @Param("country") String country,
            @Param("city") String city,
            @Param("iataCode") String iataCode
    );

    Airport findByName(String name);
}
